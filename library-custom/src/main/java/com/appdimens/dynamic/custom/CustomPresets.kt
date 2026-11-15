/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-dynamic.git
 *
 * Library: AppDimens — Custom scaling strategies
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
package com.appdimens.dynamic.custom

/**
 * EN The built-in strategies expressed as custom configs — starting points for
 * [ScalingStrategy.extend] (`BuiltInStrategies.POWER.extend("soft") { curve = power(0.6f) }`)
 * and for JSON `"extends": "power"`. Their factors match the built-in modules
 * (default `SMALL_WIDTH` path, no aspect ratio).
 *
 * PT As estratégias embutidas expressas como configs custom — ponto de partida para
 * [ScalingStrategy.extend] e para `"extends"` no JSON.
 */
object BuiltInStrategies {
    /** EN `sw / 300` — same as `sdp`. */
    @JvmField val SCALED = ScalingStrategy("scaled", ScalingConfig())

    /** EN `(sw / 300)^0.75` — same as `pwsdp`. */
    @JvmField val POWER = ScalingStrategy("power", ScalingConfig(curve = ScalingCurve.Power(0.75f)))

    /** EN `1 + 0.4·ln(sw / 300)` — same as `logsdp`. */
    @JvmField val LOGARITHMIC = ScalingStrategy("logarithmic", ScalingConfig(curve = ScalingCurve.Logarithmic(0.4f)))

    /** EN `1 + (sw / 300 − 1)·0.5` — same as `isdp`. */
    @JvmField val INTERPOLATED = ScalingStrategy("interpolated", ScalingConfig(curve = ScalingCurve.Interpolated(0.5f)))

    /** EN `√(w² + h²) / 611.63` — same as the diagonal module. */
    @JvmField val DIAGONAL = ScalingStrategy("diagonal", ScalingConfig(axis = ScalingAxis.DIAGONAL))

    /** EN `(w + h) / 833` — same as the perimeter module. */
    @JvmField val PERIMETER = ScalingStrategy("perimeter", ScalingConfig(axis = ScalingAxis.PERIMETER))

    /** EN Plain dp (factor 1). */
    @JvmField val FIXED = ScalingStrategy("fixed", ScalingConfig(curve = ScalingCurve.Constant(1f)))

    @JvmStatic
    val all: List<ScalingStrategy> get() = listOf(SCALED, POWER, LOGARITHMIC, INTERPOLATED, DIAGONAL, PERIMETER, FIXED)

    @JvmStatic
    fun byName(name: String): ScalingStrategy? = all.firstOrNull { it.id.equals(name, ignoreCase = true) }
}

/**
 * EN Ready-made strategies for common product needs. Use as-is, or `extend` them.
 * PT Estratégias prontas para necessidades comuns. Use direto ou com `extend`.
 */
object CustomPresets {
    /**
     * EN Linear on phones; damped (`power 0.5`) on tablets/desktop so large screens do not
     * get oversized UI. Factor capped at 1.8.
     * PT Linear em phones; amortecido em tablets/desktop. Fator limitado a 1.8.
     */
    @JvmField val TABLET_FRIENDLY: ScalingStrategy = scalingStrategy("tabletFriendly", register = false) {
        clamp(max = 1.8f)
        on(DeviceClass.TABLET, DeviceClass.DESKTOP, name = "large") { curve = power(0.5f) }
    }

    /**
     * EN Designed on a compact phone: grows at half speed and stays within 0.9–1.4×.
     * PT Desenhado em phone compacto: cresce pela metade e fica entre 0.9–1.4×.
     */
    @JvmField val COMPACT_FIRST: ScalingStrategy = scalingStrategy("compactFirst", register = false) {
        reference(width = 360f, height = 640f)
        curve = interpolated(0.5f)
        clamp(min = 0.9f, max = 1.4f)
    }

    /**
     * EN 10-foot UI: on TV, scale by window height against a 540 dp frame, 1×–2×.
     * PT Interface de 10 pés: na TV, escala pela altura contra 540 dp, 1×–2×.
     */
    @JvmField val TV_SAFE: ScalingStrategy = scalingStrategy("tvSafe", register = false) {
        on(DeviceClass.TV, name = "tv") {
            axis = ScalingAxis.HEIGHT
            reference(width = 960f, height = 540f)
            clamp(min = 1f, max = 2f)
        }
    }

    /**
     * EN Wear OS: scale against a 192 dp round face and keep within ±15 %.
     * PT Wear OS: escala contra tela de 192 dp e fica dentro de ±15 %.
     */
    @JvmField val WEARABLE: ScalingStrategy = scalingStrategy("wearable", register = false) {
        on(DeviceClass.WATCH, name = "watch") {
            axis = ScalingAxis.MIN
            reference(width = 192f, height = 192f)
            clamp(min = 0.85f, max = 1.15f)
        }
    }

    /**
     * EN Game HUD: shorter side, sublinear growth, aspect-ratio aware, fixed-size text.
     * PT HUD de jogo: menor lado, crescimento sublinear, sensível à proporção, texto fixo.
     */
    @JvmField val GAME_HUD: ScalingStrategy = scalingStrategy("gameHud", register = false) {
        axis = ScalingAxis.MIN
        curve = power(0.85f)
        aspectRatio()
        clamp(min = 0.75f, max = 2.5f)
        ignoreFontScale()
    }

    /**
     * EN Accessible text: follows the user font scale up to 1.3× and damps screen growth.
     * PT Texto acessível: segue o font scale até 1.3× e amortece o crescimento.
     */
    @JvmField val READABLE_TEXT: ScalingStrategy = scalingStrategy("readableText", register = false) {
        curve = logarithmic(0.4f)
        clampFontScale(1.3f)
    }

    @JvmStatic
    val all: List<ScalingStrategy>
        get() = listOf(TABLET_FRIENDLY, COMPACT_FIRST, TV_SAFE, WEARABLE, GAME_HUD, READABLE_TEXT)

    @JvmStatic
    fun byName(name: String): ScalingStrategy? = all.firstOrNull { it.id.equals(name, ignoreCase = true) }
}
