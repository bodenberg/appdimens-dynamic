/**
 * @author Bodenberg
 *
 * EN Benchmark-facing calculation strategy (maps to library code-package entry points).
 * PT Estratégia de cálculo exposta no benchmark (mapeia para entradas do pacote code da biblioteca).
 */
package com.example.app.compose.benchmark

import android.content.Context
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.code.auto.DimenAutoDp
import com.appdimens.dynamic.code.density.DimenDensityDp
import com.appdimens.dynamic.code.diagonal.DimenDiagonalDp
import com.appdimens.dynamic.code.fill.DimenFillDp
import com.appdimens.dynamic.code.fit.DimenFitDp
import com.appdimens.dynamic.code.fluid.DimenFluidDp
import com.appdimens.dynamic.code.interpolated.DimenInterpolatedDp
import com.appdimens.dynamic.code.logarithmic.DimenLogarithmicDp
import com.appdimens.dynamic.code.percent.DimenPercentDp
import com.appdimens.dynamic.code.perimeter.DimenPerimeterDp
import com.appdimens.dynamic.code.power.DimenPowerDp

/**
 * EN Which AppDimens calculation family drives Micro + Calculation benchmarks.
 * PT Qual família de cálculo AppDimens alimenta os benchmarks Micro + Cálculo.
 */
enum class BenchmarkCalculationMode {
    SCALED,
    DENSITY,
    FILL,
    FIT,
    FLUID,
    DIAGONAL,
    INTERPOLATED,
    LOGARITHMIC,
    PERCENT,
    PERIMETER,
    POWER,
    AUTO;

    val displayLabel: String
        get() = when (this) {
            SCALED       -> "Scaled (sdp)"
            DENSITY      -> "Density"
            FILL         -> "Fill"
            FIT          -> "Fit"
            FLUID        -> "Fluid"
            DIAGONAL     -> "Diagonal"
            INTERPOLATED -> "Interpolated"
            LOGARITHMIC  -> "Logarithmic"
            PERCENT      -> "Percent"
            PERIMETER    -> "Perimeter"
            POWER        -> "Power"
            AUTO         -> "Auto"
        }
}

/**
 * EN Smallest-width / height / width / +AR resolvers mirroring the scaled benchmark mix.
 * PT Resoluções sw / h / w / +AR espelhando o mix do benchmark scaled.
 */
internal data class BenchmarkDimenOps(
    val sdp: (Context, Int) -> Float,
    val hdp: (Context, Int) -> Float,
    val wdp: (Context, Int) -> Float,
    val sdpa: (Context, Int) -> Float,
)

internal fun BenchmarkCalculationMode.ops(): BenchmarkDimenOps = when (this) {
    BenchmarkCalculationMode.SCALED ->
        BenchmarkDimenOps(DimenSdp::sdp, DimenSdp::hdp, DimenSdp::wdp, DimenSdp::sdpa)
    BenchmarkCalculationMode.DENSITY ->
        BenchmarkDimenOps(DimenDensityDp::dsdp, DimenDensityDp::dhdp, DimenDensityDp::dwdp, DimenDensityDp::dsdpa)
    BenchmarkCalculationMode.FILL ->
        BenchmarkDimenOps(DimenFillDp::flsdp, DimenFillDp::flhdp, DimenFillDp::flwdp, DimenFillDp::flsdpa)
    BenchmarkCalculationMode.FIT ->
        BenchmarkDimenOps(DimenFitDp::ftsdp, DimenFitDp::fthdp, DimenFitDp::ftwdp, DimenFitDp::ftsdpa)
    BenchmarkCalculationMode.FLUID ->
        BenchmarkDimenOps(DimenFluidDp::fsdp, DimenFluidDp::fhdp, DimenFluidDp::fwdp, DimenFluidDp::fsdpa)
    BenchmarkCalculationMode.DIAGONAL ->
        BenchmarkDimenOps(DimenDiagonalDp::dgsdp, DimenDiagonalDp::dghdp, DimenDiagonalDp::dgwdp, DimenDiagonalDp::dgsdpa)
    BenchmarkCalculationMode.INTERPOLATED ->
        BenchmarkDimenOps(DimenInterpolatedDp::isdp, DimenInterpolatedDp::ihdp, DimenInterpolatedDp::iwdp, DimenInterpolatedDp::isdpa)
    BenchmarkCalculationMode.LOGARITHMIC ->
        BenchmarkDimenOps(DimenLogarithmicDp::logsdp, DimenLogarithmicDp::loghdp, DimenLogarithmicDp::logwdp, DimenLogarithmicDp::logsdpa)
    BenchmarkCalculationMode.PERCENT ->
        BenchmarkDimenOps(DimenPercentDp::psdp, DimenPercentDp::phdp, DimenPercentDp::pwdp, DimenPercentDp::psdpa)
    BenchmarkCalculationMode.PERIMETER ->
        BenchmarkDimenOps(DimenPerimeterDp::prsdp, DimenPerimeterDp::prhdp, DimenPerimeterDp::prwdp, DimenPerimeterDp::prsdpa)
    BenchmarkCalculationMode.POWER ->
        BenchmarkDimenOps(DimenPowerDp::pwsdp, DimenPowerDp::pwhdp, DimenPowerDp::pwwdp, DimenPowerDp::pwsdpa)
    BenchmarkCalculationMode.AUTO ->
        BenchmarkDimenOps(DimenAutoDp::asdp, DimenAutoDp::ahdp, DimenAutoDp::awdp, DimenAutoDp::asdpa)
}
