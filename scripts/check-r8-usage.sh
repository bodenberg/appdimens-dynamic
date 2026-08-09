#!/usr/bin/env bash
#
# check-r8-usage.sh — verifies that the public API consumed by app code still
# survives R8 full mode in the release build.
#
# Reads R8's usage.txt (app/build/outputs/mapping/release/usage.txt) and the
# must-keep list (scripts/r8-must-keep.txt). For every entry, the script
# requires at least one matching line in usage.txt that is NOT marked removed.
#
# usage.txt lines look like:
#   com.appdimens.dynamic.core.DimenCache
#   com.appdimens.dynamic.core.DimenCache.getOrPut(JLkotlin/jvm/functions/Function0;)F     removed
#
# An entry is "alive" when a matching line exists without the "removed" suffix.
# A class that R8 eliminated entirely has no kept line at all -> FAIL.

set -u

usage_file="${1:-app/build/outputs/mapping/release/usage.txt}"
must_keep="${2:-scripts/r8-must-keep.txt}"

if [[ ! -r "$must_keep" ]]; then
  echo "ERROR: must-keep list not found: $must_keep" >&2
  exit 2
fi
if [[ ! -r "$usage_file" ]]; then
  echo "ERROR: usage.txt not found: $usage_file (did :app:assembleRelease run?)" >&2
  exit 2
fi

# Secondary sources: seeds.txt reports exactly what the -keep rules protected
# (a fully-kept class has NO line in usage.txt — nothing was removed from it),
# and mapping.txt lists every class that survived in the output under a header
# "<original> -> <kept-name>:". Both are produced by R8 next to usage.txt.
seeds_file="${usage_file%/usage.txt}/seeds.txt"
mapping_file="${usage_file%/usage.txt}/mapping.txt"

# Convert a must-keep entry into an extended-regex anchored at line start.
# Literal '.' and '$' are escaped; '**' / '*' stay wildcards.
ere_of() {
  local s="$1"
  s="${s//\//.}"
  s="${s//\$/\$\$}"
  s="${s//\*\*/XK_STAR_STAR}"
  s="${s//\*/[^. ]*}"
  s="${s//XK_STAR_STAR/.*}"
  printf '^%s' "$s"
}

# The entry survives if ANY of the sources proves it:
#   usage.txt   : at least one line is not marked "removed" (partial shrink)
#   seeds.txt   : the entry was protected by a keep rule (fully retained)
#   mapping.txt : a class header "<entry> -> <name>:" exists (present in output)
survives() {
  local entry="$1" pattern
  pattern="$(ere_of "$entry")"
  if grep -E "$pattern" "$usage_file" | grep -v 'removed$' | grep -vq '#'; then return 0; fi
  if [[ -r "$seeds_file" ]] && grep -E "$pattern" "$seeds_file" | grep -vq '#'; then return 0; fi
  if [[ -r "$mapping_file" ]] && grep -E "$pattern.*->.*:$" "$mapping_file" | grep -vq '#'; then return 0; fi
  return 1
}

fail=0
checked=0
while IFS= read -r entry || [[ -n "$entry" ]]; do
  # skip blanks and comments
  [[ -n "$entry" ]] || continue
  [[ "$entry" != \#* ]] || continue

  checked=$((checked + 1))
  if survives "$entry"; then
    echo "OK   $entry"
  else
    echo "FAIL $entry  -> absent from usage.txt (kept-only), seeds.txt and mapping.txt"
    fail=1
  fi
done < "$must_keep"

if [[ "$checked" -eq 0 ]]; then
  echo "ERROR: no must-keep entries parsed from $must_keep" >&2
  exit 2
fi

if [[ "$fail" -eq 0 ]]; then
  echo "PASS: $checked must-keep surfaces survived R8 full mode."
else
  echo "FAIL: $fail entries did not survive R8. Fix keep rules or the surface." >&2
fi
exit "$fail"