/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 *
 * EN Kotlin/XML ExampleActivity — Comprehensive demo of all AppDimens SDP code features.
 * PT Kotlin/XML ExampleActivity — Demonstração completa de todos os recursos do AppDimens SDP via código.
 */
package com.example.app.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.code.scaledDp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.code.units.DimenPhysicalUnits
import com.example.app.databinding.ActivitySdpBinding

class ExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySdpBinding

    companion object {
        private const val TAG = "AppDimensExample"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // EN Data Binding Setup
        // PT Configuração do Data Binding
        binding = ActivitySdpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 1. CORE FUNCTIONS — getDimensionInPx / getResourceId       ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Get dimension value in pixels using different qualifiers.
        // PT Obtém o valor da dimensão em pixels usando diferentes qualificadores.
        val sdp25px = DimenSdp.getDimensionInPx(this, DpQualifier.SMALL_WIDTH, 25)
        val hdp25px = DimenSdp.getDimensionInPx(this, DpQualifier.HEIGHT, 25)
        val wdp25px = DimenSdp.getDimensionInPx(this, DpQualifier.WIDTH, 25)
        Log.d(TAG, "1. Core — 25.sdp=${sdp25px}px, 25.hdp=${hdp25px}px, 25.wdp=${wdp25px}px")

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 2. SHORTCUT FUNCTIONS — sdp / hdp / wdp                    ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Quick shortcuts — equivalent to getDimensionInPx but shorter syntax.
        // PT Atalhos rápidos — equivalentes ao getDimensionInPx mas com sintaxe mais curta.
        val sdp16 = DimenSdp.sdp(this, 16)   // EN Smallest Width based / PT Baseado na menor largura
        val hdp32 = DimenSdp.hdp(this, 32)   // EN Height based / PT Baseado na altura
        val wdp100 = DimenSdp.wdp(this, 100) // EN Width based / PT Baseado na largura
        Log.d(TAG, "2. Shortcuts — sdp(16)=${sdp16}px, hdp(32)=${hdp32}px, wdp(100)=${wdp100}px")

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 3. INVERTER SHORTCUTS — sdpPh / sdpLw / hdpLw / wdpLh     ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Inverter shortcuts: orientation-aware dimension switching.
        // PT Atalhos inversores: troca de dimensão com base na orientação.

        // EN sdpPh: Smallest Width by default, but in Portrait → Height (hDP)
        // PT sdpPh: Menor largura por padrão, mas em Retrato → Altura (hDP)
        val sdpPh30 = DimenSdp.sdpPh(this, 30)
        Log.d(TAG, "3. Inverter — sdpPh(30)=${sdpPh30}px")

        // EN sdpLw: Smallest Width by default, but in Landscape → Width (wDP)
        // PT sdpLw: Menor largura por padrão, mas em Paisagem → Largura (wDP)
        val sdpLw30 = DimenSdp.sdpLw(this, 30)
        Log.d(TAG, "3. Inverter — sdpLw(30)=${sdpLw30}px")

        // EN hdpLw: Height by default, but in Landscape → Width (wDP)
        // PT hdpLw: Altura por padrão, mas em Paisagem → Largura (wDP)
        val hdpLw50 = DimenSdp.hdpLw(this, 50)
        Log.d(TAG, "3. Inverter — hdpLw(50)=${hdpLw50}px")

        // EN wdpLh: Width by default, but in Landscape → Height (hDP)
        // PT wdpLh: Largura por padrão, mas em Paisagem → Altura (hDP)
        val wdpLh50 = DimenSdp.wdpLh(this, 50)
        Log.d(TAG, "3. Inverter — wdpLh(50)=${wdpLh50}px")

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 4. FACILITATORS — sdpRotate / sdpMode / sdpQualifier       ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN sdpRotate: Use a different value when the device is in a specific orientation.
        // PT sdpRotate: Usa um valor diferente quando o dispositivo está em uma orientação específica.
        // EN Example: 30.sdp normally, but 45.sdp (via SW) when LANDSCAPE.
        // PT Exemplo: 30.sdp normalmente, mas 45.sdp (via SW) quando PAISAGEM.
        val rotateVal = DimenSdp.sdpRotate(this, 30, 45)
        Log.d(TAG, "4. Rotate — sdpRotate(30, 45)=${rotateVal}px")

        // EN sdpRotate with custom qualifier and orientation:
        // PT sdpRotate com qualificador e orientação customizados:
        // EN 60.sdp default, 40 resolved as HEIGHT in PORTRAIT.
        // PT 60.sdp padrão, 40 resolvido como ALTURA em RETRATO.
        val rotateCustom = DimenSdp.sdpRotate(this, 60, 40, DpQualifier.HEIGHT, Orientation.PORTRAIT)
        Log.d(TAG, "4. Rotate Custom — sdpRotate(60, 40, HEIGHT, PORTRAIT)=${rotateCustom}px")

        // EN sdpMode: Use a different value based on UiModeType (TV, Car, Watch, etc.)
        // PT sdpMode: Usa um valor diferente baseado no UiModeType (TV, Carro, Relógio, etc.)
        val modeVal = DimenSdp.sdpMode(this, 30, 200, UiModeType.TELEVISION)
        Log.d(TAG, "4. Mode — sdpMode(30, 200, TV)=${modeVal}px (30 default, 200 on TV)")

        // EN sdpQualifier: Use a different value when a screen metric meets a threshold.
        // PT sdpQualifier: Usa um valor diferente quando uma métrica da tela atinge um limite.
        // EN 30.sdp default, but 80.sdp when smallestScreenWidthDp >= 600
        // PT 30.sdp padrão, mas 80.sdp quando smallestScreenWidthDp >= 600
        val qualifierVal = DimenSdp.sdpQualifier(this, 30, 80, DpQualifier.SMALL_WIDTH, 600)
        Log.d(TAG, "4. Qualifier — sdpQualifier(30, 80, SW, 600)=${qualifierVal}px")

        // EN sdpScreen: Combined UiModeType + DpQualifier condition.
        // PT sdpScreen: Condição combinada UiModeType + DpQualifier.
        // EN 30.sdp default, but 150.sdp on TV with sw >= 600
        // PT 30.sdp padrão, mas 150.sdp na TV com sw >= 600
        val screenVal = DimenSdp.sdpScreen(this, 30, 150, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)
        Log.d(TAG, "4. Screen — sdpScreen(30, 150, TV, SW, 600)=${screenVal}px")

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 5. DimenScaled BUILDER — Complex Conditional Chains        ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN The DimenScaled builder: define a base value and multiple conditional overrides.
        // PT O builder DimenScaled: define um valor base e múltiplas substituições condicionais.
        val scaled = DimenSdp.scaled(100)
            // EN Priority 1: TV with smallest width >= 600 → use 250
            // PT Prioridade 1: TV com menor largura >= 600 → usar 250
            .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
            // EN Priority 2: Any TV → use 500
            // PT Prioridade 2: Qualquer TV → usar 500
            .screen(UiModeType.TELEVISION, 500)
            // EN Priority 2: Foldable open → use 200
            // PT Prioridade 2: Dobrável aberto → usar 200
            .screen(UiModeType.FOLD_OPEN, 200)
            // EN Priority 3: Any device with smallest width >= 600 → use 150
            // PT Prioridade 3: Qualquer dispositivo com menor largura >= 600 → usar 150
            .screen(DpQualifier.SMALL_WIDTH, 600, 150)
            // EN Priority 4: Landscape orientation → use 120
            // PT Prioridade 4: Orientação paisagem → usar 120
            .screen(Orientation.LANDSCAPE, 120)

        // EN Resolve the final value using different qualifiers
        // PT Resolve o valor final usando diferentes qualificadores
        val scaledSdp = scaled.sdp(this)     // EN Resolves using smallest width / PT Resolve usando menor largura
        val scaledHdp = scaled.hdp(this)     // EN Resolves using height / PT Resolve usando altura
        val scaledWdp = scaled.wdp(this)     // EN Resolves using width / PT Resolve usando largura
        Log.d(TAG, "5. DimenScaled — sdp=${scaledSdp}px, hdp=${scaledHdp}px, wdp=${scaledWdp}px")

        // EN Alternative: Using Int extension scaledSdp() to start the chain
        // PT Alternativa: Usando a extensão Int scaledSdp() para iniciar a cadeia
        val scaledAlt = 80.scaledDp()
            .screen(UiModeType.TELEVISION, 300)
            .screen(DpQualifier.SMALL_WIDTH, 600, 120)
            .sdp(this)
        Log.d(TAG, "5. DimenScaled Alt — 80.scaledSdp()...sdp=${scaledAlt}px")

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 6. PHYSICAL UNITS — DimenPhysicalUnits                     ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Convert physical units (cm, mm, inches) to dp.
        // PT Converte unidades físicas (cm, mm, polegadas) para dp.
        val dpFromCm = DimenPhysicalUnits.toDpFromCm(2.5f, resources)
        val dpFromMm = DimenPhysicalUnits.toDpFromMm(25f, resources)
        val dpFromIn = DimenPhysicalUnits.toDpFromInch(1f, resources)
        Log.d(TAG, "6. Physical — 2.5cm=${dpFromCm}dp, 25mm=${dpFromMm}dp, 1in=${dpFromIn}dp")
    }
}