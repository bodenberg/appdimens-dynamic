# AppDimens Dynamic documentation — strategies by package

This folder goes deeper into each **scaling strategy** in [AppDimens Dynamic](../README.md): what it is, the formula, how to import it, and when to pick each mode. Each strategy’s code lives in `com.appdimens.dynamic.compose.<strategy>` and `com.appdimens.dynamic.code.<strategy>` with **no cross-imports** between strategies.

For **cache, bypass, and performance**, see also [library/PERFORMANCE.md](../library/PERFORMANCE.md).

**Naming parity (`compose` vs `code`):** In `library/src/main/java/com/appdimens/dynamic/`, each strategy folder pairs **`Dimen<Strategy>DpExtensions.kt`** (layout facilitators → `Float` px + `Context`) with **`Dimen<Strategy>SpExtensions.kt`** where Sp facilitators exist — the same filenames as under `compose/<strategy>/`, so it is easy to jump between UI toolkits. **Scaled** uses **`DimenSdpExtensions.kt`** and **`DimenSspExtensions.kt`** inside the `scaled/` subfolder (packages stay top-level `compose` / `code`). **Plain** View helpers remain in **`Dimen<Strategy>PlainPx.kt`** per strategy plus shared logic in **`com.appdimens.dynamic.code.plain`**.

**Compose API (catálogo scaled, convenções de nomes e comportamento espelhado nas outras estratégias):** [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md) — inclui **§4.5** helpers View/`code` **Plain** (`Float` px + `Context`, `Dimen*PlainPx.kt` por estratégia, lógica partilhada em `com.appdimens.dynamic.code.plain`). **Resize** por restrições (`autoResize*`, `ResizeBound`, `compose.resize` / `code.resize`) está em [resize.md](resize.md) e no KDoc: [`compose.resize`](KDOC/com.appdimens.dynamic.compose.resize/index.md), [`code.resize`](KDOC/com.appdimens.dynamic.code.resize/index.md).

**KDoc API reference** (gerado a partir do KDoc `/** … */` da library — pacotes, tipos, membros): [index.md](index.md). Os ficheiros por símbolo estão em [`KDOC/`](KDOC/) (caminhos curtos sem `[` / `]` para o GitHub resolver links Markdown).

**Atualizar `KDOC/` a partir do HTML do Dokka:** com a saída já gerada em [`DOCUMENTATION2/`](../DOCUMENTATION2) (ver [`library/build.gradle.kts`](../library/build.gradle.kts)), execute na raiz do repo: `python3 scripts/sync_kdoc_from_dokka_html.py` — reescreve [`DOCUMENTATION/KDOC/`](KDOC/) e o ficheiro [`package-list`](KDOC/package-list) a partir desse HTML (sem precisar de voltar a correr o Gradle neste passo). Depois de alterar código ou KDoc, gere HTML com `./gradlew :library:dokkaGenerateHtml` e volte a correr o script para manter nomes e membros alinhados (por exemplo **`unitSizeInDp`** em vez de **`unitSizePerPx`**).

**Nota:** o Dokka pode mostrar `ERROR CLASS` em tipos Compose nas páginas exportadas; isso reflecte a resolução de classpath na geração, não um erro da library. As páginas narrativas (`scaled.md`, [library/PERFORMANCE.md](../library/PERFORMANCE.md), [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md)) podem estar mais actualizadas em texto corrido do que tabelas KDoc até sincronizar.

## Summary

| Strategy | Document |
|----------|----------|
| Scaled (default SDP / HDP / WDP) | [scaled.md](scaled.md) |
| Percent (linear 1/300 + `space*`) | [percent.md](percent.md) |
| Power (sublinear) | [power.md](power.md) |
| Fluid (320–768 dp band) | [fluid.md](fluid.md) |
| Auto (linear + log after 480 dp) | [auto.md](auto.md) |
| Diagonal | [diagonal.md](diagonal.md) |
| Fill (“cover”) | [fill.md](fill.md) |
| Fit (“contain”) | [fit.md](fit.md) |
| Interpolated | [interpolated.md](interpolated.md) |
| Logarithmic | [logarithmic.md](logarithmic.md) |
| Perimeter | [perimeter.md](perimeter.md) |
| Density | [density.md](density.md) |
| Resize (constraint-based auto-fit) | [resize.md](resize.md) |
| Physical units (mm, cm, in) | [physical-units.md](physical-units.md) |

### Quick links

0. [KDoc API — root index](index.md)  
1. [Compose API reference — conventions & scaled catalog](COMPOSE-API-CONVENTIONS.md)  
2. [Scaled](scaled.md) — recommended starting point  
3. [Percent](percent.md)  
4. [Power](power.md)  
5. [Fluid](fluid.md)  
6. [Auto](auto.md)  
7. [Diagonal](diagonal.md)  
8. [Fill](fill.md)  
9. [Fit](fit.md)  
10. [Interpolated](interpolated.md)  
11. [Logarithmic](logarithmic.md)  
12. [Perimeter](perimeter.md)  
13. [Density](density.md)  
14. [Resize](resize.md)  
15. [Physical units](physical-units.md)  

## Suggested decision flow

```mermaid
flowchart LR
  start[New_layout]
  scaled[scaled_sdp_hdp_wdp]
  qa[QA_phone_tablet]
  other[Other_strategy]
  start --> scaled
  scaled --> qa
  qa -->|curve_not_right| other
```

Always start with **scaled**; switch strategy only where visual QA or requirements (TV, ultrawide, split-screen) need a different growth curve.
