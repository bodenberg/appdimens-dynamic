//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.percent](../index.md)/[DimenPercent](index.md)/[reorderEntries](reorder-entries.md)

# reorderEntries

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomDpEntry](../-custom-dp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

EN Adds a new entry and re-sorts the list. Sorting is crucial: first by priority (ascending), and then by dpQualifierEntry.value (descending) so that larger qualifiers (e.g., sw600dp) are checked before smaller qualifiers (e.g., sw320dp).

PT Adiciona uma nova entrada e reordena a lista. A ordenação é crucial: primeiro por priority (crescente), e depois por dpQualifierEntry.value (decrescente) para que qualificadores maiores (e.g., sw600dp) sejam verificados antes de qualificadores menores (e.g., sw320dp).
