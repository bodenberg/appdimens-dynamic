/**
 * EN Routes Compose demo dimensions to the selected scaling strategy (same formulas as each library package).
 * PT Encaminha as dimensões do demo Compose para a estratégia escolhida (mesmas fórmulas de cada pacote).
 */
package com.example.app.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.compose.auto.asdp
import com.appdimens.dynamic.compose.auto.asdpa
import com.appdimens.dynamic.compose.auto.asdpLh
import com.appdimens.dynamic.compose.auto.asdpLw
import com.appdimens.dynamic.compose.auto.asdpMode
import com.appdimens.dynamic.compose.auto.asdpPh
import com.appdimens.dynamic.compose.auto.asdpQualifier
import com.appdimens.dynamic.compose.auto.asdpRotate
import com.appdimens.dynamic.compose.auto.asdpRotatePlain
import com.appdimens.dynamic.compose.auto.asdpScreen
import com.appdimens.dynamic.compose.auto.asdpModePlain
import com.appdimens.dynamic.compose.auto.asdpQualifierPlain
import com.appdimens.dynamic.compose.auto.asdpScreenPlain
import com.appdimens.dynamic.compose.auto.ahdp
import com.appdimens.dynamic.compose.auto.ahdpLw
import com.appdimens.dynamic.compose.auto.autoDp
import com.appdimens.dynamic.compose.auto.awdp
import com.appdimens.dynamic.compose.auto.awdpLh
import com.appdimens.dynamic.compose.density.densityDp
import com.appdimens.dynamic.compose.density.dsdpa
import com.appdimens.dynamic.compose.density.dhdp
import com.appdimens.dynamic.compose.density.dhdpLw
import com.appdimens.dynamic.compose.density.dsdp
import com.appdimens.dynamic.compose.density.dsdpLh
import com.appdimens.dynamic.compose.density.dsdpLw
import com.appdimens.dynamic.compose.density.dsdpMode
import com.appdimens.dynamic.compose.density.dsdpPh
import com.appdimens.dynamic.compose.density.dsdpQualifier
import com.appdimens.dynamic.compose.density.dsdpRotate
import com.appdimens.dynamic.compose.density.dsdpRotatePlain
import com.appdimens.dynamic.compose.density.dsdpScreen
import com.appdimens.dynamic.compose.density.dsdpModePlain
import com.appdimens.dynamic.compose.density.dsdpQualifierPlain
import com.appdimens.dynamic.compose.density.dsdpScreenPlain
import com.appdimens.dynamic.compose.density.dwdp
import com.appdimens.dynamic.compose.density.dwdpLh
import com.appdimens.dynamic.compose.diagonal.diagonalDp
import com.appdimens.dynamic.compose.diagonal.dgsdpa
import com.appdimens.dynamic.compose.diagonal.dghdp
import com.appdimens.dynamic.compose.diagonal.dghdpLw
import com.appdimens.dynamic.compose.diagonal.dgsdp
import com.appdimens.dynamic.compose.diagonal.dgsdpLh
import com.appdimens.dynamic.compose.diagonal.dgsdpLw
import com.appdimens.dynamic.compose.diagonal.dgsdpMode
import com.appdimens.dynamic.compose.diagonal.dgsdpPh
import com.appdimens.dynamic.compose.diagonal.dgsdpQualifier
import com.appdimens.dynamic.compose.diagonal.dgsdpRotate
import com.appdimens.dynamic.compose.diagonal.dgsdpRotatePlain
import com.appdimens.dynamic.compose.diagonal.dgsdpScreen
import com.appdimens.dynamic.compose.diagonal.dgsdpModePlain
import com.appdimens.dynamic.compose.diagonal.dgsdpQualifierPlain
import com.appdimens.dynamic.compose.diagonal.dgsdpScreenPlain
import com.appdimens.dynamic.compose.diagonal.dgwdp
import com.appdimens.dynamic.compose.diagonal.dgwdpLh
import com.appdimens.dynamic.compose.fill.fillDp
import com.appdimens.dynamic.compose.fill.flsdpa
import com.appdimens.dynamic.compose.fill.flhdp
import com.appdimens.dynamic.compose.fill.flhdpLw
import com.appdimens.dynamic.compose.fill.flsdp
import com.appdimens.dynamic.compose.fill.flsdpLh
import com.appdimens.dynamic.compose.fill.flsdpLw
import com.appdimens.dynamic.compose.fill.flsdpMode
import com.appdimens.dynamic.compose.fill.flsdpPh
import com.appdimens.dynamic.compose.fill.flsdpQualifier
import com.appdimens.dynamic.compose.fill.flsdpRotate
import com.appdimens.dynamic.compose.fill.flsdpRotatePlain
import com.appdimens.dynamic.compose.fill.flsdpScreen
import com.appdimens.dynamic.compose.fill.flsdpModePlain
import com.appdimens.dynamic.compose.fill.flsdpQualifierPlain
import com.appdimens.dynamic.compose.fill.flsdpScreenPlain
import com.appdimens.dynamic.compose.fill.flwdp
import com.appdimens.dynamic.compose.fill.flwdpLh
import com.appdimens.dynamic.compose.fit.fitDp
import com.appdimens.dynamic.compose.fit.ftsdpa
import com.appdimens.dynamic.compose.fit.fthdp
import com.appdimens.dynamic.compose.fit.fthdpLw
import com.appdimens.dynamic.compose.fit.ftsdp
import com.appdimens.dynamic.compose.fit.ftsdpLh
import com.appdimens.dynamic.compose.fit.ftsdpLw
import com.appdimens.dynamic.compose.fit.ftsdpMode
import com.appdimens.dynamic.compose.fit.ftsdpPh
import com.appdimens.dynamic.compose.fit.ftsdpQualifier
import com.appdimens.dynamic.compose.fit.ftsdpRotate
import com.appdimens.dynamic.compose.fit.ftsdpRotatePlain
import com.appdimens.dynamic.compose.fit.ftsdpScreen
import com.appdimens.dynamic.compose.fit.ftsdpModePlain
import com.appdimens.dynamic.compose.fit.ftsdpQualifierPlain
import com.appdimens.dynamic.compose.fit.ftsdpScreenPlain
import com.appdimens.dynamic.compose.fit.ftwdp
import com.appdimens.dynamic.compose.fit.ftwdpLh
import com.appdimens.dynamic.compose.fluid.fluidDp
import com.appdimens.dynamic.compose.fluid.fsdpa
import com.appdimens.dynamic.compose.fluid.fhdp
import com.appdimens.dynamic.compose.fluid.fhdpLw
import com.appdimens.dynamic.compose.fluid.fsdp
import com.appdimens.dynamic.compose.fluid.fsdpLh
import com.appdimens.dynamic.compose.fluid.fsdpLw
import com.appdimens.dynamic.compose.fluid.fsdpMode
import com.appdimens.dynamic.compose.fluid.fsdpPh
import com.appdimens.dynamic.compose.fluid.fsdpQualifier
import com.appdimens.dynamic.compose.fluid.fsdpRotate
import com.appdimens.dynamic.compose.fluid.fsdpRotatePlain
import com.appdimens.dynamic.compose.fluid.fsdpScreen
import com.appdimens.dynamic.compose.fluid.fsdpModePlain
import com.appdimens.dynamic.compose.fluid.fsdpQualifierPlain
import com.appdimens.dynamic.compose.fluid.fsdpScreenPlain
import com.appdimens.dynamic.compose.fluid.fwdp
import com.appdimens.dynamic.compose.fluid.fwdpLh
import com.appdimens.dynamic.compose.interpolated.interpolatedDp
import com.appdimens.dynamic.compose.interpolated.isdpa
import com.appdimens.dynamic.compose.interpolated.ihdp
import com.appdimens.dynamic.compose.interpolated.ihdpLw
import com.appdimens.dynamic.compose.interpolated.isdp
import com.appdimens.dynamic.compose.interpolated.isdpLh
import com.appdimens.dynamic.compose.interpolated.isdpLw
import com.appdimens.dynamic.compose.interpolated.isdpMode
import com.appdimens.dynamic.compose.interpolated.isdpPh
import com.appdimens.dynamic.compose.interpolated.isdpQualifier
import com.appdimens.dynamic.compose.interpolated.isdpRotate
import com.appdimens.dynamic.compose.interpolated.isdpRotatePlain
import com.appdimens.dynamic.compose.interpolated.isdpScreen
import com.appdimens.dynamic.compose.interpolated.isdpModePlain
import com.appdimens.dynamic.compose.interpolated.isdpQualifierPlain
import com.appdimens.dynamic.compose.interpolated.isdpScreenPlain
import com.appdimens.dynamic.compose.interpolated.iwdp
import com.appdimens.dynamic.compose.interpolated.iwdpLh
import com.appdimens.dynamic.compose.logarithmic.logarithmicDp
import com.appdimens.dynamic.compose.logarithmic.logsdpa
import com.appdimens.dynamic.compose.logarithmic.loghdp
import com.appdimens.dynamic.compose.logarithmic.loghdpLw
import com.appdimens.dynamic.compose.logarithmic.logsdp
import com.appdimens.dynamic.compose.logarithmic.logsdpLh
import com.appdimens.dynamic.compose.logarithmic.logsdpLw
import com.appdimens.dynamic.compose.logarithmic.logsdpMode
import com.appdimens.dynamic.compose.logarithmic.logsdpPh
import com.appdimens.dynamic.compose.logarithmic.logsdpQualifier
import com.appdimens.dynamic.compose.logarithmic.logsdpRotate
import com.appdimens.dynamic.compose.logarithmic.logsdpRotatePlain
import com.appdimens.dynamic.compose.logarithmic.logsdpScreen
import com.appdimens.dynamic.compose.logarithmic.logsdpModePlain
import com.appdimens.dynamic.compose.logarithmic.logsdpQualifierPlain
import com.appdimens.dynamic.compose.logarithmic.logsdpScreenPlain
import com.appdimens.dynamic.compose.logarithmic.logwdp
import com.appdimens.dynamic.compose.logarithmic.logwdpLh
import com.appdimens.dynamic.compose.percent.percentDp
import com.appdimens.dynamic.compose.percent.psdpa
import com.appdimens.dynamic.compose.percent.phdp
import com.appdimens.dynamic.compose.percent.phdpLw
import com.appdimens.dynamic.compose.percent.psdp
import com.appdimens.dynamic.compose.percent.psdpLh
import com.appdimens.dynamic.compose.percent.psdpLw
import com.appdimens.dynamic.compose.percent.psdpMode
import com.appdimens.dynamic.compose.percent.psdpPh
import com.appdimens.dynamic.compose.percent.psdpQualifier
import com.appdimens.dynamic.compose.percent.psdpRotate
import com.appdimens.dynamic.compose.percent.psdpRotatePlain
import com.appdimens.dynamic.compose.percent.psdpScreen
import com.appdimens.dynamic.compose.percent.psdpModePlain
import com.appdimens.dynamic.compose.percent.psdpQualifierPlain
import com.appdimens.dynamic.compose.percent.psdpScreenPlain
import com.appdimens.dynamic.compose.percent.pwdp
import com.appdimens.dynamic.compose.percent.pwdpLh
import com.appdimens.dynamic.compose.perimeter.perimeterDp
import com.appdimens.dynamic.compose.perimeter.prsdpa
import com.appdimens.dynamic.compose.perimeter.prhdp
import com.appdimens.dynamic.compose.perimeter.prhdpLw
import com.appdimens.dynamic.compose.perimeter.prsdp
import com.appdimens.dynamic.compose.perimeter.prsdpLh
import com.appdimens.dynamic.compose.perimeter.prsdpLw
import com.appdimens.dynamic.compose.perimeter.prsdpMode
import com.appdimens.dynamic.compose.perimeter.prsdpPh
import com.appdimens.dynamic.compose.perimeter.prsdpQualifier
import com.appdimens.dynamic.compose.perimeter.prsdpRotate
import com.appdimens.dynamic.compose.perimeter.prsdpRotatePlain
import com.appdimens.dynamic.compose.perimeter.prsdpScreen
import com.appdimens.dynamic.compose.perimeter.prsdpModePlain
import com.appdimens.dynamic.compose.perimeter.prsdpQualifierPlain
import com.appdimens.dynamic.compose.perimeter.prsdpScreenPlain
import com.appdimens.dynamic.compose.perimeter.prwdp
import com.appdimens.dynamic.compose.perimeter.prwdpLh
import com.appdimens.dynamic.compose.power.powerDp
import com.appdimens.dynamic.compose.power.pwsdpa
import com.appdimens.dynamic.compose.power.pwhdp
import com.appdimens.dynamic.compose.power.pwhdpLw
import com.appdimens.dynamic.compose.power.pwsdp
import com.appdimens.dynamic.compose.power.pwsdpLh
import com.appdimens.dynamic.compose.power.pwsdpLw
import com.appdimens.dynamic.compose.power.pwsdpMode
import com.appdimens.dynamic.compose.power.pwsdpPh
import com.appdimens.dynamic.compose.power.pwsdpQualifier
import com.appdimens.dynamic.compose.power.pwsdpRotate
import com.appdimens.dynamic.compose.power.pwsdpRotatePlain
import com.appdimens.dynamic.compose.power.pwsdpScreen
import com.appdimens.dynamic.compose.power.pwsdpModePlain
import com.appdimens.dynamic.compose.power.pwsdpQualifierPlain
import com.appdimens.dynamic.compose.power.pwsdpScreenPlain
import com.appdimens.dynamic.compose.power.pwwdp
import com.appdimens.dynamic.compose.power.pwwdpLh
import com.appdimens.dynamic.compose.auto.assp
import com.appdimens.dynamic.compose.auto.sspRotatePlain as stratSspRotatePlainAuto
import com.appdimens.dynamic.compose.density.dssp
import com.appdimens.dynamic.compose.density.sspRotatePlain as stratSspRotatePlainDensity
import com.appdimens.dynamic.compose.diagonal.dgssp
import com.appdimens.dynamic.compose.diagonal.sspRotatePlain as stratSspRotatePlainDiagonal
import com.appdimens.dynamic.compose.fill.flssp
import com.appdimens.dynamic.compose.fill.sspRotatePlain as stratSspRotatePlainFill
import com.appdimens.dynamic.compose.fit.ftssp
import com.appdimens.dynamic.compose.fit.sspRotatePlain as stratSspRotatePlainFit
import com.appdimens.dynamic.compose.fluid.fssp
import com.appdimens.dynamic.compose.fluid.sspRotatePlain as stratSspRotatePlainFluid
import com.appdimens.dynamic.compose.interpolated.issp
import com.appdimens.dynamic.compose.interpolated.sspRotatePlain as stratSspRotatePlainInterpolated
import com.appdimens.dynamic.compose.logarithmic.logssp
import com.appdimens.dynamic.compose.logarithmic.sspRotatePlain as stratSspRotatePlainLogarithmic
import com.appdimens.dynamic.compose.percent.pssp
import com.appdimens.dynamic.compose.percent.sspRotatePlain as stratSspRotatePlainPercent
import com.appdimens.dynamic.compose.perimeter.prssp
import com.appdimens.dynamic.compose.perimeter.sspRotatePlain as stratSspRotatePlainPerimeter
import com.appdimens.dynamic.compose.power.pwssp
import com.appdimens.dynamic.compose.power.sspRotatePlain as stratSspRotatePlainPower
import com.appdimens.dynamic.compose.ssp
import com.appdimens.dynamic.compose.sspRotatePlain as stratSspRotatePlainScaled
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.sdpRotatePlain
import com.appdimens.dynamic.compose.sdpModePlain
import com.appdimens.dynamic.compose.sdpQualifierPlain
import com.appdimens.dynamic.compose.sdpScreenPlain
import com.appdimens.dynamic.compose.sdpa
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.hdpLw
import com.appdimens.dynamic.compose.scaledDp
import com.appdimens.dynamic.compose.sdpLh
import com.appdimens.dynamic.compose.sdpLw
import com.appdimens.dynamic.compose.sdpMode
import com.appdimens.dynamic.compose.sdpPh
import com.appdimens.dynamic.compose.sdpQualifier
import com.appdimens.dynamic.compose.sdpRotate
import com.appdimens.dynamic.compose.sdpScreen
import com.appdimens.dynamic.compose.wdp
import com.appdimens.dynamic.compose.wdpLh

enum class DemoCalcStrategy(val labelEn: String, val labelPt: String) {
    Scaled("Scaled (default)", "Scaled (padrão)"),
    Percent("Percent", "Percent"),
    Power("Power", "Power"),
    Auto("Auto", "Auto"),
    Logarithmic("Logarithmic", "Logarítmico"),
    Fluid("Fluid", "Fluid"),
    Interpolated("Interpolated", "Interpolado"),
    Diagonal("Diagonal", "Diagonal"),
    Perimeter("Perimeter", "Perímetro"),
    Fit("Fit", "Fit"),
    Fill("Fill", "Fill"),
    Density("Density", "Densidade"),
}

val LocalDemoCalcStrategy = compositionLocalOf { DemoCalcStrategy.Scaled }

@get:Composable
val Number.demoSwDp: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.sdp
            DemoCalcStrategy.Percent -> this.psdp
            DemoCalcStrategy.Power -> this.pwsdp
            DemoCalcStrategy.Auto -> this.asdp
            DemoCalcStrategy.Logarithmic -> this.logsdp
            DemoCalcStrategy.Fluid -> this.fsdp
            DemoCalcStrategy.Interpolated -> this.isdp
            DemoCalcStrategy.Diagonal -> this.dgsdp
            DemoCalcStrategy.Perimeter -> this.prsdp
            DemoCalcStrategy.Fit -> this.ftsdp
            DemoCalcStrategy.Fill -> this.flsdp
            DemoCalcStrategy.Density -> this.dsdp
        }

@get:Composable
val Number.demoSwDpa: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.sdpa
            DemoCalcStrategy.Percent -> this.psdpa
            DemoCalcStrategy.Power -> this.pwsdpa
            DemoCalcStrategy.Auto -> this.asdpa
            DemoCalcStrategy.Logarithmic -> this.logsdpa
            DemoCalcStrategy.Fluid -> this.fsdpa
            DemoCalcStrategy.Interpolated -> this.isdpa
            DemoCalcStrategy.Diagonal -> this.dgsdpa
            DemoCalcStrategy.Perimeter -> this.prsdpa
            DemoCalcStrategy.Fit -> this.ftsdpa
            DemoCalcStrategy.Fill -> this.flsdpa
            DemoCalcStrategy.Density -> this.dsdpa
        }

@get:Composable
val Number.demoHdp: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.hdp
            DemoCalcStrategy.Percent -> this.phdp
            DemoCalcStrategy.Power -> this.pwhdp
            DemoCalcStrategy.Auto -> this.ahdp
            DemoCalcStrategy.Logarithmic -> this.loghdp
            DemoCalcStrategy.Fluid -> this.fhdp
            DemoCalcStrategy.Interpolated -> this.ihdp
            DemoCalcStrategy.Diagonal -> this.dghdp
            DemoCalcStrategy.Perimeter -> this.prhdp
            DemoCalcStrategy.Fit -> this.fthdp
            DemoCalcStrategy.Fill -> this.flhdp
            DemoCalcStrategy.Density -> this.dhdp
        }

@get:Composable
val Number.demoWdp: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.wdp
            DemoCalcStrategy.Percent -> this.pwdp
            DemoCalcStrategy.Power -> this.pwwdp
            DemoCalcStrategy.Auto -> this.awdp
            DemoCalcStrategy.Logarithmic -> this.logwdp
            DemoCalcStrategy.Fluid -> this.fwdp
            DemoCalcStrategy.Interpolated -> this.iwdp
            DemoCalcStrategy.Diagonal -> this.dgwdp
            DemoCalcStrategy.Perimeter -> this.prwdp
            DemoCalcStrategy.Fit -> this.ftwdp
            DemoCalcStrategy.Fill -> this.flwdp
            DemoCalcStrategy.Density -> this.dwdp
        }

@get:Composable
val Number.demoSwPh: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.sdpPh
            DemoCalcStrategy.Percent -> this.psdpPh
            DemoCalcStrategy.Power -> this.pwsdpPh
            DemoCalcStrategy.Auto -> this.asdpPh
            DemoCalcStrategy.Logarithmic -> this.logsdpPh
            DemoCalcStrategy.Fluid -> this.fsdpPh
            DemoCalcStrategy.Interpolated -> this.isdpPh
            DemoCalcStrategy.Diagonal -> this.dgsdpPh
            DemoCalcStrategy.Perimeter -> this.prsdpPh
            DemoCalcStrategy.Fit -> this.ftsdpPh
            DemoCalcStrategy.Fill -> this.flsdpPh
            DemoCalcStrategy.Density -> this.dsdpPh
        }

@get:Composable
val Number.demoSwLw: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.sdpLw
            DemoCalcStrategy.Percent -> this.psdpLw
            DemoCalcStrategy.Power -> this.pwsdpLw
            DemoCalcStrategy.Auto -> this.asdpLw
            DemoCalcStrategy.Logarithmic -> this.logsdpLw
            DemoCalcStrategy.Fluid -> this.fsdpLw
            DemoCalcStrategy.Interpolated -> this.isdpLw
            DemoCalcStrategy.Diagonal -> this.dgsdpLw
            DemoCalcStrategy.Perimeter -> this.prsdpLw
            DemoCalcStrategy.Fit -> this.ftsdpLw
            DemoCalcStrategy.Fill -> this.flsdpLw
            DemoCalcStrategy.Density -> this.dsdpLw
        }

@get:Composable
val Number.demoHLw: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.hdpLw
            DemoCalcStrategy.Percent -> this.phdpLw
            DemoCalcStrategy.Power -> this.pwhdpLw
            DemoCalcStrategy.Auto -> this.ahdpLw
            DemoCalcStrategy.Logarithmic -> this.loghdpLw
            DemoCalcStrategy.Fluid -> this.fhdpLw
            DemoCalcStrategy.Interpolated -> this.ihdpLw
            DemoCalcStrategy.Diagonal -> this.dghdpLw
            DemoCalcStrategy.Perimeter -> this.prhdpLw
            DemoCalcStrategy.Fit -> this.fthdpLw
            DemoCalcStrategy.Fill -> this.flhdpLw
            DemoCalcStrategy.Density -> this.dhdpLw
        }

@get:Composable
val Number.demoWLh: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.wdpLh
            DemoCalcStrategy.Percent -> this.pwdpLh
            DemoCalcStrategy.Power -> this.pwwdpLh
            DemoCalcStrategy.Auto -> this.awdpLh
            DemoCalcStrategy.Logarithmic -> this.logwdpLh
            DemoCalcStrategy.Fluid -> this.fwdpLh
            DemoCalcStrategy.Interpolated -> this.iwdpLh
            DemoCalcStrategy.Diagonal -> this.dgwdpLh
            DemoCalcStrategy.Perimeter -> this.prwdpLh
            DemoCalcStrategy.Fit -> this.ftwdpLh
            DemoCalcStrategy.Fill -> this.flwdpLh
            DemoCalcStrategy.Density -> this.dwdpLh
        }

@get:Composable
val Number.demoSwLh: Dp
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.sdpLh
            DemoCalcStrategy.Percent -> this.psdpLh
            DemoCalcStrategy.Power -> this.pwsdpLh
            DemoCalcStrategy.Auto -> this.asdpLh
            DemoCalcStrategy.Logarithmic -> this.logsdpLh
            DemoCalcStrategy.Fluid -> this.fsdpLh
            DemoCalcStrategy.Interpolated -> this.isdpLh
            DemoCalcStrategy.Diagonal -> this.dgsdpLh
            DemoCalcStrategy.Perimeter -> this.prsdpLh
            DemoCalcStrategy.Fit -> this.ftsdpLh
            DemoCalcStrategy.Fill -> this.flsdpLh
            DemoCalcStrategy.Density -> this.dsdpLh
        }

@Composable
fun Int.demoSdpRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled ->
            this.sdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Percent ->
            this.psdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Power ->
            this.pwsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Auto ->
            this.asdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Logarithmic ->
            this.logsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fluid ->
            this.fsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Interpolated ->
            this.isdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Diagonal ->
            this.dgsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Perimeter ->
            this.prsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fit ->
            this.ftsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fill ->
            this.flsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Density ->
            this.dsdpRotate(rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

@Composable
fun Int.demoSdpMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Percent -> this.psdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Power -> this.pwsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Auto -> this.asdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Logarithmic -> this.logsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fluid -> this.fsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Interpolated -> this.isdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Diagonal -> this.dgsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Perimeter -> this.prsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fit -> this.ftsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fill -> this.flsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Density -> this.dsdpMode(modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

@Composable
fun Number.demoSdpQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Percent -> this.psdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Power -> this.pwsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Auto -> this.asdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Logarithmic -> this.logsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fluid -> this.fsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Interpolated -> this.isdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Diagonal -> this.dgsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Perimeter -> this.prsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fit -> this.ftsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fill -> this.flsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Density -> this.dsdpQualifier(qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

@Composable
fun Number.demoSdpScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Percent -> this.psdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Power -> this.pwsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Auto -> this.asdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Logarithmic -> this.logsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fluid -> this.fsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Interpolated -> this.isdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Diagonal -> this.dgsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Perimeter -> this.prsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fit -> this.ftsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Fill -> this.flsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        DemoCalcStrategy.Density -> this.dsdpScreen(screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

@Composable
fun Dp.demoSdpRotatePlain(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Percent -> this.psdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Power -> this.pwsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Auto -> this.asdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Logarithmic -> this.logsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Fluid -> this.fsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Interpolated -> this.isdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Diagonal -> this.dgsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Perimeter -> this.prsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Fit -> this.ftsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Fill -> this.flsdpRotatePlain(rotation, orientation)
        DemoCalcStrategy.Density -> this.dsdpRotatePlain(rotation, orientation)
    }

@Composable
fun Dp.demoSdpModePlain(mode: Dp, uiModeType: UiModeType): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Percent -> this.psdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Power -> this.pwsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Auto -> this.asdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Logarithmic -> this.logsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Fluid -> this.fsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Interpolated -> this.isdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Diagonal -> this.dgsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Perimeter -> this.prsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Fit -> this.ftsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Fill -> this.flsdpModePlain(mode, uiModeType)
        DemoCalcStrategy.Density -> this.dsdpModePlain(mode, uiModeType)
    }

@Composable
fun Dp.demoSdpQualifierPlain(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Percent -> this.psdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Power -> this.pwsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Auto -> this.asdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Logarithmic -> this.logsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Fluid -> this.fsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Interpolated -> this.isdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Diagonal -> this.dgsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Perimeter -> this.prsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Fit -> this.ftsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Fill -> this.flsdpQualifierPlain(qualified, qualifierType, qualifierValue)
        DemoCalcStrategy.Density -> this.dsdpQualifierPlain(qualified, qualifierType, qualifierValue)
    }

@Composable
fun Dp.demoSdpScreenPlain(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Dp =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.sdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Percent -> this.psdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Power -> this.pwsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Auto -> this.asdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Logarithmic -> this.logsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Fluid -> this.fsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Interpolated -> this.isdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Diagonal -> this.dgsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Perimeter -> this.prsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Fit -> this.ftsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Fill -> this.flsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
        DemoCalcStrategy.Density -> this.dsdpScreenPlain(screen, uiModeType, qualifierType, qualifierValue)
    }

@get:Composable
val Number.demoSsp: TextUnit
    get() =
        when (LocalDemoCalcStrategy.current) {
            DemoCalcStrategy.Scaled -> this.ssp
            DemoCalcStrategy.Percent -> this.pssp
            DemoCalcStrategy.Power -> this.pwssp
            DemoCalcStrategy.Auto -> this.assp
            DemoCalcStrategy.Logarithmic -> this.logssp
            DemoCalcStrategy.Fluid -> this.fssp
            DemoCalcStrategy.Interpolated -> this.issp
            DemoCalcStrategy.Diagonal -> this.dgssp
            DemoCalcStrategy.Perimeter -> this.prssp
            DemoCalcStrategy.Fit -> this.ftssp
            DemoCalcStrategy.Fill -> this.flssp
            DemoCalcStrategy.Density -> this.dssp
        }

@Composable
fun TextUnit.demoSspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit =
    when (LocalDemoCalcStrategy.current) {
        DemoCalcStrategy.Scaled -> this.stratSspRotatePlainScaled(rotation, orientation)
        DemoCalcStrategy.Percent -> this.stratSspRotatePlainPercent(rotation, orientation)
        DemoCalcStrategy.Power -> this.stratSspRotatePlainPower(rotation, orientation)
        DemoCalcStrategy.Auto -> this.stratSspRotatePlainAuto(rotation, orientation)
        DemoCalcStrategy.Logarithmic -> this.stratSspRotatePlainLogarithmic(rotation, orientation)
        DemoCalcStrategy.Fluid -> this.stratSspRotatePlainFluid(rotation, orientation)
        DemoCalcStrategy.Interpolated -> this.stratSspRotatePlainInterpolated(rotation, orientation)
        DemoCalcStrategy.Diagonal -> this.stratSspRotatePlainDiagonal(rotation, orientation)
        DemoCalcStrategy.Perimeter -> this.stratSspRotatePlainPerimeter(rotation, orientation)
        DemoCalcStrategy.Fit -> this.stratSspRotatePlainFit(rotation, orientation)
        DemoCalcStrategy.Fill -> this.stratSspRotatePlainFill(rotation, orientation)
        DemoCalcStrategy.Density -> this.stratSspRotatePlainDensity(rotation, orientation)
    }

@Composable
fun demoDimenScaledResultDp(): Dp {
    val mode = LocalDemoCalcStrategy.current
    return when (mode) {
        DemoCalcStrategy.Scaled ->
            100.scaledDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .sdp
        DemoCalcStrategy.Percent ->
            100.percentDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .psdp
        DemoCalcStrategy.Power ->
            100.powerDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .pwsdp
        DemoCalcStrategy.Auto ->
            100.autoDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .asdp
        DemoCalcStrategy.Logarithmic ->
            100.logarithmicDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .logsdp
        DemoCalcStrategy.Fluid ->
            100.fluidDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .fsdp
        DemoCalcStrategy.Interpolated ->
            100.interpolatedDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .isdp
        DemoCalcStrategy.Diagonal ->
            100.diagonalDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .dgsdp
        DemoCalcStrategy.Perimeter ->
            100.perimeterDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .prsdp
        DemoCalcStrategy.Fit ->
            100.fitDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .ftsdp
        DemoCalcStrategy.Fill ->
            100.fillDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .flsdp
        DemoCalcStrategy.Density ->
            100.densityDp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
                .screen(UiModeType.TELEVISION, 500)
                .screen(UiModeType.FOLD_OPEN, 200)
                .screen(DpQualifier.SMALL_WIDTH, 600, 150)
                .screen(Orientation.LANDSCAPE, 120)
                .dsdp
    }
}
