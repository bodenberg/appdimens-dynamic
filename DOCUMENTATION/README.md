# AppDimens Dynamic documentation — strategies by package

This folder goes deeper into each **scaling strategy** in [AppDimens Dynamic](../README.md): what it is, the formula, how to import it, and when to pick each mode. Each strategy’s code lives in `com.appdimens.dynamic.compose.<strategy>` and `com.appdimens.dynamic.code.<strategy>` with **no cross-imports** between strategies.

For **cache, bypass, and performance**, see also [library/PERFORMANCE.md](../library/PERFORMANCE.md).

**Full Compose API (every `Number` property, facilitators, builders, prefix map):** [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md)

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

0. [Compose API reference — conventions & scaled catalog](COMPOSE-API-CONVENTIONS.md)  
1. [Scaled](scaled.md) — recommended starting point  
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
14. [Physical units](physical-units.md)  

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
