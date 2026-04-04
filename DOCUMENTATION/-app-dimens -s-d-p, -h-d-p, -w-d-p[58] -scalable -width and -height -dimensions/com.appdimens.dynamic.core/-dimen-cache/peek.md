//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[peek](peek.md)

# peek

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

fun [peek](peek.md)(key: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)?

EN Reads a stored cache value without computing a fallback. Returns `null` on a miss.

**Bypass interaction:**[getOrPut](get-or-put.md) intentionally **does not write** to the shard table for certain cheap calculation types when aspect ratio is off (see fast-path bypass in [getOrPut](get-or-put.md)). For those keys, [peek](peek.md) will typically return `null` even after [getOrPut](get-or-put.md) returned a value — the result was computed but not persisted. Use [getOrPut](get-or-put.md) when you need the resolved float; use [peek](peek.md) only to probe entries that were actually stored.

PT Lê um valor gravado no cache sem calcular fallback. Retorna `null` em miss.

**Interação com bypass:** para chaves que seguem o bypass de [getOrPut](get-or-put.md), o valor não é guardado na tabela; [peek](peek.md) costuma devolver `null` mesmo após um [getOrPut](get-or-put.md) bem-sucedido.