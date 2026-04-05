//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.perimeter](../index.md)/[DimenPerimeter](index.md)/[resolve](resolve.md)

# resolve

[jvm]
private fun [resolve](resolve.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): <Error class: unknown class>

EN Resolves the final dimension. This is the Composable part that reads the current configuration and decides which Dp to use.

PT Resolve a dimensão final. Esta é a parte Composable que lê a configuração atual e decide qual Dp usar.

#### Parameters

jvm

| | |
|---|---|
| qualifier | The dynamic scaling qualifier that will be applied at the end (SMALL_WIDTH, HEIGHT, or WIDTH), determined by the access property (.sdp, .hdp, .wdp). |