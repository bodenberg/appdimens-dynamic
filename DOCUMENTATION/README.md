# AppDimens Dynamic documentation — strategies by package

This folder goes deeper into each **scaling strategy** in [AppDimens Dynamic](../README.md): what it is, the formula, how to import it, and when to pick each mode. Each strategy’s code lives in `com.appdimens.dynamic.compose.<strategy>` and `com.appdimens.dynamic.code.<strategy>` with **no cross-imports** between strategies.

For **cache, bypass, and performance**, see also [library/PERFORMANCE.md](../library/PERFORMANCE.md).

**Full Compose API (every `Number` property, facilitators, builders, prefix map):** [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md)

**KDoc API reference** (generated from library `/** … */` KDoc — packages, types, members): [index.md](index.md). Symbol files live under [`KDOC/`](KDOC/) (short path without `[` / `]` so GitHub resolves relative Markdown links correctly).

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
