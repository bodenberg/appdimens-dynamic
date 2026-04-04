# Estratégia Scaled (`compose.scaled` / `code` espelhado)

## O que é

A estratégia **scaled** é o modo **padrão** do AppDimens Dynamic: escala valores de design em torno da referência de **300 dp** no eixo escolhido (**smallest width**, **altura** ou **largura** da janela). Corresponde ao comportamento clássico SDP / HDP / WDP com cache, qualificadores e integração com `Configuration`.

## Cálculo utilizado

- Lê-se a dimensão em dp do eixo efetivo (após **inversores** de orientação, se usados).
- **Sem** `applyAspectRatio` (`a` / `ia`): resultado ≈ `base × (dim / 300)` — fator `1/300` (`INV_BASE_RATIO` em `DimenCache`).
- **Com** `applyAspectRatio`: usa a curva refinada em `DimenCache.calculateRawScaling`, combinando desvio da dimensão em relação a 300 dp com ajuste ligado ao log do aspeto normalizado do ecrã.
- Com `ignoreMultiWindows` (`i` / `ia`) ativo e heurística de multi-janela: devolve o **valor base** sem escala.
- Constante de referência: `BASE_WIDTH_DP = 300f` em `DesignScaleConstants` (eixo usado depende do qualificador).

Sufixos habituais: **`a`** = aspect ratio; **`i`** = ignorar multi-janela; **`ia`** = ambos. Inversores (ex.: `.sdpPh`, `.sdpLw`) trocam o eixo efetivo consoante orientação.

## Como usar

**Compose** — importe extensões de `com.appdimens.dynamic.compose.scaled` (ou o pacote que expõe `sdp`, `hdp`, `wdp`, `ssp`, etc.):

```kotlin
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.wdp

Modifier.padding(16.sdp).width(100.wdp)
```

**Código (Views / Kotlin)** — `com.appdimens.dynamic.code.DimenSdp`, `DimenSsp`, extensões `ssp`, `scaledSp()`, etc.

Cada estratégia expõe o mesmo **padrão de ficheiros**: `Dimen*Dp`, `*DpExtensions`, `*Scaled`, `*Sp`, `*SpExtensions`, `*SpScaled` (em `scaled`, os templates de Sp podem usar nomes como `DimenSsp*` no código legado).

## Por que usar

É o equilíbrio da biblioteca: **previsível**, bem integrado com **DimenCache**, suporta **builders** (`scaledDp()` / `scaledSp()`), facilitadores (rotação, modo UI, qualifier) e inversores — sem curvas exóticas.

## Em que caso usar

- Layouts de aplicação normais (telefone, tablet, foldable).
- Quando quiser o mesmo “look” que a documentação principal descreve como referência.
- Sempre que não houver razão forte para percent puro, power, fluid, etc.

## Vantagens e trade-offs

- **Vantagens:** curva familiar; ótimo desempenho (incluindo bypass de cache em caminhos quentes sem AR); documentação e exemplos abundantes no README.
- **Trade-offs:** em ecrãs muito grandes, o crescimento linear (sem `a`) pode parecer agressivo frente a **power** ou **fluid**; nesse caso avalie outra estratégia só nos componentes afetados.

## Estratégia de uso recomendada

Use **SDP** (`.sdp`) para espaçamentos e tamanhos que devem manter-se coerentes à rotação; **HDP** para listas verticais; **WDP** quando a largura disponível deve dominar. Ative **`a`** se ratios não standard degradarem o layout. Só depois de QA, substitua pontualmente por outra estratégia documentada neste índice.

[Voltar ao índice](README.md)
