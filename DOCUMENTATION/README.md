# Documentação AppDimens Dynamic — estratégias por pacote

Esta pasta aprofunda cada **estratégia de escala** da biblioteca [AppDimens Dynamic](../README.md): o que é, fórmula, como importar e quando escolher cada modo. O código de cada estratégia está isolado em `com.appdimens.dynamic.compose.<estrategia>` e `com.appdimens.dynamic.code.<estrategia>` (sem dependências cruzadas entre estratégias).

Para **cache, bypass e desempenho**, consulte também [library/PERFORMANCE.md](../library/PERFORMANCE.md).

## Sumário

| Estratégia | Documento |
|------------|-----------|
| Scaled (SDP / HDP / WDP padrão) | [scaled.md](scaled.md) |
| Percent (linear 1/300 + `space*`) | [percent.md](percent.md) |
| Power (sublinear) | [power.md](power.md) |
| Fluid (plateaus 320–768 dp) | [fluid.md](fluid.md) |
| Auto (linear + log após 480 dp) | [auto.md](auto.md) |
| Diagonal | [diagonal.md](diagonal.md) |
| Fill (“cover”) | [fill.md](fill.md) |
| Fit (“contain”) | [fit.md](fit.md) |
| Interpolated | [interpolated.md](interpolated.md) |
| Logarithmic | [logarithmic.md](logarithmic.md) |
| Perimeter | [perimeter.md](perimeter.md) |
| Density | [density.md](density.md) |
| Resize (auto-ajuste em constraints) | [resize.md](resize.md) |
| Unidades físicas (mm, cm, pol) | [unidades-fisicas.md](unidades-fisicas.md) |

### Lista com links rápidos

1. [Scaled](scaled.md) — ponto de partida recomendado.
2. [Percent](percent.md)
3. [Power](power.md)
4. [Fluid](fluid.md)
5. [Auto](auto.md)
6. [Diagonal](diagonal.md)
7. [Fill](fill.md)
8. [Fit](fit.md)
9. [Interpolated](interpolated.md)
10. [Logarithmic](logarithmic.md)
11. [Perimeter](perimeter.md)
12. [Density](density.md)
13. [Resize](resize.md)
14. [Unidades físicas](unidades-fisicas.md)

## Fluxo de decisão sugerido

```mermaid
flowchart LR
  start[Layout_novo]
  scaled[scaled_sdp_hdp_wdp]
  qa[QA_em_telefone_tablet]
  other[Outra_estrategia]
  start --> scaled
  scaled --> qa
  qa -->|curva_nao_serve| other
```

Comece sempre com **scaled**; mude de estratégia só onde a revisão visual ou requisitos (TV, ultrawide, split-screen) exigirem outra curva de crescimento.
