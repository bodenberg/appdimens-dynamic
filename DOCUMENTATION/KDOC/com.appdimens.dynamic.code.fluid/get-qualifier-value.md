//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.fluid](index.md)/[getQualifierValue](get-qualifier-value.md)

# getQualifierValue

[jvm]
internal fun [getQualifierValue](get-qualifier-value.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Gets the actual value from the Configuration for the given DpQualifier.

PT Obtém o valor real da configuração (Configuration) para o DpQualifier dado.

#### Return

The numeric value (in Dp) of the screen metric.

#### Parameters

jvm

| | |
|---|---|
| qualifier | The type of qualifier (SMALL_WIDTH, HEIGHT, WIDTH). |
| configuration | The current resource configuration. |