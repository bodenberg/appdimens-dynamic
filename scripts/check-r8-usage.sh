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

# Convert a must-keep entry into an extended-regex anchored at line start.
# Literal '.' is escaped; '**' / '*' stay wildcards.
ere_of() {
  local s="$1"
  s="${s//\//.}"
  s="${s//\*\*/XK_STAR_STAR}"
  s="${s//\*/[^. ]*}"
  s="${s//XK_STAR_STAR/.*}"
  printf '^%s' "$s"
}

fail=0
checked=0
while IFS= read -r entry || [[ -n "$entry" ]]; do
  # skip blanks and comments
  [[ -n "$entry" ]] || continue
  [[ "$entry" != \#* ]] || continue

  checked=$((checked + 1))
  pattern="$(ere_of "$entry")"
  # A kept line matches the anchored pattern and is not suffixed by "removed".
  if grep -E "$pattern" "$usage_file" | grep -v 'removed$' | grep -vq '#'; then
    echo "OK   $entry"
  else
    echo "FAIL $entry  -> no kept member found in usage.txt"
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