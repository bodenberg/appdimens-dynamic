/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * Physical units conversion utilities for AppDimens Android Code library,
 * providing conversion between physical measurements and Dp/Px/Sp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.dynamic.code.units

import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.dynamic.common.UnitType

/**
 * EN Utility class for physical unit conversions.
 * PT Classe utilitária para conversões de unidades físicas.
 */
object DimenPhysicalUnits {

    // MARK: - Conversion Methods

    /**
     * EN Converts millimeters to Dp.
     * PT Converte milímetros para Dp.
     */
    @JvmStatic
    fun toDpFromMm(mm: Float, resources: Resources): Float {
        val dm = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, dm) / dm.density
    }

    /**
     * EN Converts centimeters to Dp.
     * PT Converte centímetros para Dp.
     */
    @JvmStatic
    fun toDpFromCm(cm: Float, resources: Resources): Float {
        val dm = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, cm * 10f, dm) / dm.density
    }

    /**
     * EN Converts inches to Dp.
     * PT Converte polegadas para Dp.
     */
    @JvmStatic
    fun toDpFromInch(inch: Float, resources: Resources): Float {
        val dm = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inch, dm) / dm.density
    }

    /**
     * EN Converts millimeters to Pixels.
     * PT Converte milímetros para Pixels.
     */
    @JvmStatic
    fun toPxFromMm(mm: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, resources.displayMetrics)

    /**
     * EN Converts centimeters to Pixels.
     * PT Converte centímetros para Pixels.
     */
    @JvmStatic
    fun toPxFromCm(cm: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, cm * 10f, resources.displayMetrics)

    /**
     * EN Converts inches to Pixels.
     * PT Converte polegadas para Pixels.
     */
    @JvmStatic
    fun toPxFromInch(inch: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inch, resources.displayMetrics)

    /**
     * EN Converts millimeters to SP.
     * PT Converte milímetros para SP.
     */
    @JvmStatic
    fun toSpFromMm(mm: Float, resources: Resources): Float {
        val dm = resources.displayMetrics
        return toPxFromMm(mm, resources) / dm.scaledDensity
    }

    /**
     * EN Converts centimeters to SP.
     * PT Converte centímetros para SP.
     */
    @JvmStatic
    fun toSpFromCm(cm: Float, resources: Resources): Float {
        val dm = resources.displayMetrics
        return toPxFromCm(cm, resources) / dm.scaledDensity
    }

    /**
     * EN Converts inches to SP.
     * PT Converte polegadas para SP.
     */
    @JvmStatic
    fun toSpFromInch(inch: Float, resources: Resources): Float {
        val dm = resources.displayMetrics
        return toPxFromInch(inch, resources) / dm.scaledDensity
    }

    // MARK: - Utility Methods

    /**
     * EN Converts a diameter value in a specific physical unit to radius in Dp.
     * @param diameter The diameter value.
     * @param unitType The unit type (mm, cm, inch).
     * @param resources The Context's Resources.
     * @return The radius in Dp.
     * PT Converte um valor de diâmetro em uma unidade física específica para raio em Dp.
     * @param diameter O valor do diâmetro.
     * @param unitType O tipo de unidade (mm, cm, inch).
     * @param resources Os Resources do Context.
     * @return O raio em Dp.
     */
    @JvmStatic
    fun radiusFromDiameter(diameter: Float, unitType: UnitType, resources: Resources): Float {
        val diameterInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(diameter, resources)
            UnitType.CM -> toDpFromCm(diameter, resources)
            UnitType.INCH -> toDpFromInch(diameter, resources)
            UnitType.DP -> diameter
            UnitType.SP -> diameter * (resources.displayMetrics.scaledDensity / resources.displayMetrics.density)
            UnitType.PX -> diameter / resources.displayMetrics.density
        }
        
        return diameterInDp / 2.0f
    }

    /**
     * EN Converts a circumference value in a specific physical unit to radius in Dp.
     * @param circumference The circumference value.
     * @param unitType The unit type (mm, cm, inch, dp, sp, px).
     * @param resources The Context's Resources.
     * @return The radius in Dp.
     * PT Converte um valor de circunferência em uma unidade física específica para raio em Dp.
     * @param circumference O valor da circunferência.
     * @param unitType O tipo de unidade (mm, cm, inch, dp, sp, px).
     * @param resources Os Resources do Context.
     * @return O raio em Dp.
     */
    @JvmStatic
    fun radiusFromCircumference(circumference: Float, unitType: UnitType, resources: Resources): Float {
        val circumferenceInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(circumference, resources)
            UnitType.CM -> toDpFromCm(circumference, resources)
            UnitType.INCH -> toDpFromInch(circumference, resources)
            UnitType.DP -> circumference
            UnitType.SP -> circumference * (resources.displayMetrics.scaledDensity / resources.displayMetrics.density)
            UnitType.PX -> circumference / resources.displayMetrics.density
        }
        
        return circumferenceInDp / (2.0f * kotlin.math.PI.toFloat())
    }

    // MARK: - Conversion Extensions
    /**
     * EN Float extension to convert MM to CM.
     * PT Extensão de Float para converter MM para CM.
     */
    @JvmStatic
    fun Float.mmToCm(): Float = this / 10.0f

    /**
     * EN Float extension to convert MM to Inch.
     * PT Extensão de Float para converter MM para Inch.
     */
    @JvmStatic
    fun Float.mmToInch(): Float = this / 25.4f

    /**
     * EN Float extension to convert CM to MM.
     * PT Extensão de Float para converter CM para MM.
     */
    @JvmStatic
    fun Float.cmToMm(): Float = this * 10.0f

    /**
     * EN Float extension to convert CM to Inch.
     * PT Extensão de Float para converter CM para Inch.
     */
    @JvmStatic
    fun Float.cmToInch(): Float = this / 2.54f

    /**
     * EN Float extension to convert Inch to MM.
     * PT Extensão de Float para converter Inch para MM.
     */
    @JvmStatic
    fun Float.inchToMm(): Float = this * 25.4f

    /**
     * EN Float extension to convert Inch to CM.
     * PT Extensão de Float para converter Inch para CM.
     */
    @JvmStatic
    fun Float.inchToCm(): Float = this * 2.54f
}