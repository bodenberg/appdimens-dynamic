/**
 * @author Bodenberg
 *
 * EN Benchmark-facing calculation strategy (maps to library code-package entry points).
 * PT Estratégia de cálculo exposta no benchmark (mapeia para entradas do pacote code da biblioteca).
 */
package com.example.app.compose.benchmark

import android.content.Context
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.code.auto.DimenAuto
import com.appdimens.dynamic.code.density.DimenDensity
import com.appdimens.dynamic.code.diagonal.DimenDiagonal
import com.appdimens.dynamic.code.fill.DimenFill
import com.appdimens.dynamic.code.fit.DimenFit
import com.appdimens.dynamic.code.fluid.DimenFluid
import com.appdimens.dynamic.code.interpolated.DimenInterpolated
import com.appdimens.dynamic.code.logarithmic.DimenLogarithmic
import com.appdimens.dynamic.code.percent.DimenPercent
import com.appdimens.dynamic.code.perimeter.DimenPerimeter
import com.appdimens.dynamic.code.power.DimenPower

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
        BenchmarkDimenOps(DimenDensity::dsdp, DimenDensity::dhdp, DimenDensity::dwdp, DimenDensity::dsdpa)
    BenchmarkCalculationMode.FILL ->
        BenchmarkDimenOps(DimenFill::flsdp, DimenFill::flhdp, DimenFill::flwdp, DimenFill::flsdpa)
    BenchmarkCalculationMode.FIT ->
        BenchmarkDimenOps(DimenFit::ftsdp, DimenFit::fthdp, DimenFit::ftwdp, DimenFit::ftsdpa)
    BenchmarkCalculationMode.FLUID ->
        BenchmarkDimenOps(DimenFluid::fsdp, DimenFluid::fhdp, DimenFluid::fwdp, DimenFluid::fsdpa)
    BenchmarkCalculationMode.DIAGONAL ->
        BenchmarkDimenOps(DimenDiagonal::dgsdp, DimenDiagonal::dghdp, DimenDiagonal::dgwdp, DimenDiagonal::dgsdpa)
    BenchmarkCalculationMode.INTERPOLATED ->
        BenchmarkDimenOps(DimenInterpolated::isdp, DimenInterpolated::ihdp, DimenInterpolated::iwdp, DimenInterpolated::isdpa)
    BenchmarkCalculationMode.LOGARITHMIC ->
        BenchmarkDimenOps(DimenLogarithmic::logsdp, DimenLogarithmic::loghdp, DimenLogarithmic::logwdp, DimenLogarithmic::logsdpa)
    BenchmarkCalculationMode.PERCENT ->
        BenchmarkDimenOps(DimenPercent::psdp, DimenPercent::phdp, DimenPercent::pwdp, DimenPercent::psdpa)
    BenchmarkCalculationMode.PERIMETER ->
        BenchmarkDimenOps(DimenPerimeter::prsdp, DimenPerimeter::prhdp, DimenPerimeter::prwdp, DimenPerimeter::prsdpa)
    BenchmarkCalculationMode.POWER ->
        BenchmarkDimenOps(DimenPower::pwsdp, DimenPower::pwhdp, DimenPower::pwwdp, DimenPower::pwsdpa)
    BenchmarkCalculationMode.AUTO ->
        BenchmarkDimenOps(DimenAuto::asdp, DimenAuto::ahdp, DimenAuto::awdp, DimenAuto::asdpa)
}
