/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 *
 * EN Java ExampleActivity — Core SDP + DimenSsp (§2b) + DimenScaled + DimenResize (§6, twin of Compose §5) + physical units + XML note.
 * PT Java — SDP + DimenSsp (§2b) + DimenScaled + DimenResize (§6, equivalente ao Compose §5) + unidades físicas + nota XML.
 */
package com.example.app.java;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.appdimens.dynamic.code.units.DimenPhysicalUnits;
import com.appdimens.dynamic.code.DimenSdp;
import com.appdimens.dynamic.code.DimenScaled;
import com.appdimens.dynamic.code.DimenSsp;
import com.appdimens.dynamic.code.resize.DimenResize;
import com.appdimens.dynamic.core.AutoResizePercentBasis;
import com.appdimens.dynamic.core.ResizeBound;
import com.appdimens.dynamic.core.ResizeRangePx;
import com.appdimens.dynamic.common.DpQualifier;
import com.appdimens.dynamic.common.Orientation;
import com.appdimens.dynamic.common.UiModeType;
import com.example.app.databinding.ActivitySdpBinding;

public class ExampleActivity extends AppCompatActivity {

    private static final String TAG = "AppDimensJavaExample";

    private ActivitySdpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // EN Data Binding Setup
        // PT Configuração do Data Binding
        binding = ActivitySdpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 1. CORE FUNCTIONS — getDimensionInPx / getResourceId       ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Get dimension value in pixels using different qualifiers.
        // PT Obtém o valor da dimensão em pixels usando diferentes qualificadores.
        float sdp25px = DimenSdp.getDimensionInPx(this, DpQualifier.SMALL_WIDTH, 25);
        float hdp25px = DimenSdp.getDimensionInPx(this, DpQualifier.HEIGHT, 25);
        float wdp25px = DimenSdp.getDimensionInPx(this, DpQualifier.WIDTH, 25);
        Log.d(TAG, "1. Core — 25.sdp=" + sdp25px + "px, 25.hdp=" + hdp25px + "px, 25.wdp=" + wdp25px + "px");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 2. SHORTCUT FUNCTIONS — sdp / hdp / wdp                    ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Quick shortcuts — equivalent to getDimensionInPx but shorter syntax.
        // PT Atalhos rápidos — equivalentes ao getDimensionInPx mas com sintaxe mais curta.
        float sdp16 = DimenSdp.sdp(this, 16);   // EN Smallest Width based / PT Baseado na menor largura
        float hdp32 = DimenSdp.hdp(this, 32);   // EN Height based / PT Baseado na altura
        float wdp100 = DimenSdp.wdp(this, 100); // EN Width based / PT Baseado na largura
        Log.d(TAG, "2. Shortcuts — sdp(16)=" + sdp16 + "px, hdp(32)=" + hdp32 + "px, wdp(100)=" + wdp100 + "px");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 2b. SCALABLE SP — DimenSsp (Compose: .ssp / .hsp / .wsp)   ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Same scaled-sp axes as Compose; values are px for setTextSize(COMPLEX_UNIT_PX, …).
        // PT Mesmos eixos sp escalável que no Compose; retorno em px para setTextSize em px.
        float ssp16px = DimenSsp.ssp(this, 16);
        float hsp20px = DimenSsp.hsp(this, 20);
        float wsp18px = DimenSsp.wsp(this, 18);
        Log.d(TAG, "2b. SSp — ssp(16)=" + ssp16px + "px, hsp(20)=" + hsp20px + "px, wsp(18)=" + wsp18px + "px");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 3. INVERTER SHORTCUTS — sdpPh / sdpLw / hdpLw / wdpLh     ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Inverter shortcuts: orientation-aware dimension switching.
        // PT Atalhos inversores: troca de dimensão com base na orientação.

        // EN sdpPh: Smallest Width by default, but in Portrait → Height (hDP)
        // PT sdpPh: Menor largura por padrão, mas em Retrato → Altura (hDP)
        float sdpPh30 = DimenSdp.sdpPh(this, 30);
        Log.d(TAG, "3. Inverter — sdpPh(30)=" + sdpPh30 + "px");

        // EN sdpLw: Smallest Width by default, but in Landscape → Width (wDP)
        // PT sdpLw: Menor largura por padrão, mas em Paisagem → Largura (wDP)
        float sdpLw30 = DimenSdp.sdpLw(this, 30);
        Log.d(TAG, "3. Inverter — sdpLw(30)=" + sdpLw30 + "px");

        // EN hdpLw: Height by default, but in Landscape → Width (wDP)
        // PT hdpLw: Altura por padrão, mas em Paisagem → Largura (wDP)
        float hdpLw50 = DimenSdp.hdpLw(this, 50);
        Log.d(TAG, "3. Inverter — hdpLw(50)=" + hdpLw50 + "px");

        // EN wdpLh: Width by default, but in Landscape → Height (hDP)
        // PT wdpLh: Largura por padrão, mas em Paisagem → Altura (hDP)
        float wdpLh50 = DimenSdp.wdpLh(this, 50);
        Log.d(TAG, "3. Inverter — wdpLh(50)=" + wdpLh50 + "px");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 4. FACILITATORS — sdpRotate / sdpMode / sdpQualifier       ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN sdpRotate: Use a different value when the device is in a specific orientation.
        // PT sdpRotate: Usa um valor diferente quando o dispositivo está em uma orientação específica.
        // EN Example: 30.sdp normally, but 45.sdp (via SW) when LANDSCAPE.
        // PT Exemplo: 30.sdp normalmente, mas 45.sdp (via SW) quando PAISAGEM.
        float rotateVal = DimenSdp.sdpRotate(this, 30, 45);
        Log.d(TAG, "4. Rotate — sdpRotate(30, 45)=" + rotateVal + "px");

        // EN sdpRotate with custom qualifier and orientation:
        // PT sdpRotate com qualificador e orientação customizados:
        // EN 60.sdp default, 40 resolved as HEIGHT in PORTRAIT.
        // PT 60.sdp padrão, 40 resolvido como ALTURA em RETRATO.
        float rotateCustom = DimenSdp.sdpRotate(this, 60, 40, DpQualifier.HEIGHT, Orientation.PORTRAIT);
        Log.d(TAG, "4. Rotate Custom — sdpRotate(60, 40, HEIGHT, PORTRAIT)=" + rotateCustom + "px");

        // EN sdpMode: Use a different value based on UiModeType (TV, Car, Watch, etc.)
        // PT sdpMode: Usa um valor diferente baseado no UiModeType (TV, Carro, Relógio, etc.)
        float modeVal = DimenSdp.sdpMode(this, 30, 200, UiModeType.TELEVISION);
        Log.d(TAG, "4. Mode — sdpMode(30, 200, TV)=" + modeVal + "px (30 default, 200 on TV)");

        // EN sdpQualifier: Use a different value when a screen metric meets a threshold.
        // PT sdpQualifier: Usa um valor diferente quando uma métrica da tela atinge um limite.
        // EN 30.sdp default, but 80.sdp when smallestScreenWidthDp >= 600
        // PT 30.sdp padrão, mas 80.sdp quando smallestScreenWidthDp >= 600
        float qualifierVal = DimenSdp.sdpQualifier(this, 30, 80, DpQualifier.SMALL_WIDTH, 600);
        Log.d(TAG, "4. Qualifier — sdpQualifier(30, 80, SW, 600)=" + qualifierVal + "px");

        // EN sdpScreen: Combined UiModeType + DpQualifier condition.
        // PT sdpScreen: Condição combinada UiModeType + DpQualifier.
        // EN 30.sdp default, but 150.sdp on TV with sw >= 600
        // PT 30.sdp padrão, mas 150.sdp na TV com sw >= 600
        float screenVal = DimenSdp.sdpScreen(this, 30, 150, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600);
        Log.d(TAG, "4. Screen — sdpScreen(30, 150, TV, SW, 600)=" + screenVal + "px");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 5. DimenScaled BUILDER — Complex Conditional Chains        ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN The DimenScaled builder: define a base value and multiple conditional overrides.
        // PT O builder DimenScaled: define um valor base e múltiplas substituições condicionais.
        DimenScaled scaled = DimenSdp.scaled(100)
            // EN Priority 1: TV with smallest width >= 600 → use 250
            // PT Prioridade 1: TV com menor largura >= 600 → usar 250
            .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, (Number)600, (Number)250)
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
            .screen(Orientation.LANDSCAPE, 120);

        // EN Resolve the final value using different qualifiers
        // PT Resolve o valor final usando diferentes qualificadores
        float scaledSdp = scaled.sdp(this);     // EN Resolves using smallest width / PT Resolve usando menor largura
        float scaledHdp = scaled.hdp(this);     // EN Resolves using height / PT Resolve usando altura
        float scaledWdp = scaled.wdp(this);     // EN Resolves using width / PT Resolve usando largura
        Log.d(TAG, "5. DimenScaled — sdp=" + scaledSdp + "px, hdp=" + scaledHdp + "px, wdp=" + scaledWdp + "px");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 6. AUTO-RESIZE (code) — DimenResize (Compose §5 equivalent)  ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Build a px range (dp / sp / % of sw|w|h) then pick largest step that fits a limit.
        // PT Constrói intervalo em px (dp/sp/% de sw|w|h) e escolhe o maior passo que cabe num limite.
        float density = getResources().getDisplayMetrics().density;
        final float containerPx = 120f * density;

        ResizeRangePx squareRange = DimenResize.rangePx(
            this,
            new ResizeBound.FixedDp(16f),
            new ResizeBound.FixedDp(100f),
            new ResizeBound.FixedDp(4f)
        );
        float iconSidePx = DimenResize.fittingPx(squareRange, candidatePx -> candidatePx <= containerPx);
        Log.d(TAG, "6. Resize dp range — largest square side px=" + iconSidePx + " (limit " + containerPx + "px)");

        ResizeRangePx textRange = DimenResize.rangePx(
            this,
            new ResizeBound.FixedSp(10f),
            new ResizeBound.FixedSp(22f),
            new ResizeBound.FixedSp(2f)
        );
        final float mockMaxTextWidthPx = 280f;
        float textSizePx = DimenResize.fittingPx(textRange, candidate -> candidate <= mockMaxTextWidthPx);
        Log.d(TAG, "6. Resize sp range — candidate text px=" + textSizePx + " (mock width " + mockMaxTextWidthPx + "px)");

        ResizeRangePx mixedRange = DimenResize.rangePx(
            this,
            new ResizeBound.FixedDp(8f),
            new ResizeBound.Percent(40f, DpQualifier.SMALL_WIDTH),
            new ResizeBound.FixedDp(4f)
        );
        float mixedPx = DimenResize.fittingPx(mixedRange, candidate -> candidate <= containerPx);
        Log.d(TAG, "6. Resize mixed — max %sw… picked px=" + mixedPx);

        float boxWpx = 360f * density;
        float boxHpx = 240f * density;
        float padPx = 12f * density;
        kotlin.Pair<Float, Float> inner = DimenResize.innerMaxDimensionsPx(
            boxWpx, boxHpx, padPx, padPx, padPx, padPx
        );
        float innerW = inner.getFirst();
        float innerH = inner.getSecond();

        ResizeRangePx pctBarRange = DimenResize.rangePxPercentOfInnerBox(
            this,
            AutoResizePercentBasis.WIDTH,
            5f,
            50f,
            2f,
            innerW,
            innerH
        );
        float barPx = DimenResize.fittingInnerWidthPx(
            pctBarRange, boxWpx, boxHpx, padPx, padPx, padPx, padPx
        );
        Log.d(TAG, "6. Resize % of inner width — bar px=" + barPx + " (innerW=" + innerW + "px)");

        float slotSquarePx = DimenResize.fittingInnerSquareSidePx(
            squareRange, boxWpx, boxHpx, padPx, padPx, padPx, padPx
        );
        Log.d(TAG, "6. Resize square in padded box — side px=" + slotSquarePx);

        TextPaint titlePaint = new TextPaint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        float titlePx = DimenResize.fittingTextSpPx(
            this,
            "Example title that scales to the inner box",
            new ResizeBound.FixedSp(10f),
            new ResizeBound.FixedSp(40f),
            innerW,
            innerH,
            titlePaint,
            new ResizeBound.FixedSp(1f),
            2,
            null,
            true,
            android.text.Layout.Alignment.ALIGN_NORMAL,
            true
        );
        Log.d(TAG, "6. Resize text (StaticLayout) — title px=" + titlePx);

        float titlePercentPx = DimenResize.fittingTextSpPercentPx(
            this,
            "Percent-based title",
            AutoResizePercentBasis.MIN_SIDE,
            4f,
            22f,
            1f,
            innerW,
            innerH,
            titlePaint,
            1,
            null,
            true,
            android.text.Layout.Alignment.ALIGN_NORMAL,
            true
        );
        Log.d(TAG, "6. Resize text % of min inner side — px=" + titlePercentPx);

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 7. PHYSICAL UNITS — DimenPhysicalUnits                     ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN Convert physical units (cm, mm, inches) to dp.
        // PT Converte unidades físicas (cm, mm, polegadas) para dp.
        float dpFromCm = DimenPhysicalUnits.toDpFromCm(2.5f, getResources());
        float dpFromMm = DimenPhysicalUnits.toDpFromMm(25f, getResources());
        float dpFromIn = DimenPhysicalUnits.toDpFromInch(1f, getResources());
        Log.d(TAG, "7. Physical — 2.5cm=" + dpFromCm + "dp, 25mm=" + dpFromMm + "dp, 1in=" + dpFromIn + "dp");

        // ╔══════════════════════════════════════════════════════════════╗
        // ║ 8. XML RESOURCE USAGE — Using dimens directly in XML       ║
        // ╚══════════════════════════════════════════════════════════════╝

        // EN In XML layouts, you can use dimension resources directly:
        // PT Em layouts XML, você pode usar recursos de dimensão diretamente:
        //
        // android:layout_width="@dimen/_50sdp"   ← Smallest Width based
        // android:layout_height="@dimen/_50hdp"   ← Height based
        // android:padding="@dimen/_16wdp"         ← Width based
        // android:textSize="@dimen/_14ssp"        ← Scalable SP (font)
        //
        // EN See activity_sdp.xml for a complete XML example.
        // PT Veja activity_sdp.xml para um exemplo completo em XML.
    }
}