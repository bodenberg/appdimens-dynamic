# Relatório de Performance: AppDimens Dynamic (Clean Start v4.0)

Este relatório reflete a performance final calibrada com a metodologia **Clean Start**: o cache é limpo de forma sincronizada uma única vez antes do início de todas as medições, garantindo um estado inicial puro.

---

## 1. PC Benchmark (Development Environment)
*Ambiente: Kotlin/JVM (Estado Pós-Limpeza)*

### A. Operação Única (Single Call - Pico)
| Tipo de Ação | Nanosegundos (ns) | Microsegundos (μs) | Milisegundos (ms) |
| :--- | :--- | :--- | :--- |
| **Cálculo Puro (Math Only)** | 3 ns | 0,003 μs | 0,000003 ms |
| **Cálculo + AspectRatio (AR)** | 6 ns | 0,006 μs | 0,000006 ms |
| **Consulta de Cache (L1 Hit)** | **4 ns** | **0,004 μs** | **0,000004 ms** |

### B. Operação em Lote (100 Calls - Pico)
| Tipo de Ação | Nanosegundos (ns) | Microsegundos (μs) | Milisegundos (ms) |
| :--- | :--- | :--- | :--- |
| **100 Cálculos (Math Only)** | 79 ns | 0,079 μs | 0,000079 ms |
| **100 Cálculos (Math + AR)** | 81 ns | 0,081 μs | 0,000081 ms |
| **100 Resoluções via Cache** | **177 ns** | **0,177 μs** | **0,000177 ms** |

---

## 2. Real Device Benchmark (Xiaomi 11T Pro)
*Hardware: Snapdragon 888 (Estado Pós-Limpeza)*

### A. Operação Única (Single Call - Pico)
| Tipo de Ação | Nanosegundos (ns) | Microsegundos (μs) | Milisegundos (ms) |
| :--- | :--- | :--- | :--- |
| **Cálculo Puro (Math Only)** | 2 ns* | 0,002 μs | 0,000002 ms |
| **Cálculo + AspectRatio (AR)** | 40 ns | 0,040 μs | 0,000040 ms |
| **Consulta de Cache (L1 Hit)** | **11 ns** | **0,011 μs** | **0,000011 ms** |

### B. Operação em Lote (100 Calls - Pico)
| Tipo de Ação | Nanosegundos (ns) | Microsegundos (μs) | Milisegundos (ms) |
| :--- | :--- | :--- | :--- |
| **100 Cálculos (Math Only)** | 173 ns | 0,173 μs | 0,000173 ms |
| **100 Cálculos (Math + AR)** | 198 ns | 0,198 μs | 0,000198 ms |
| **100 Resoluções via Cache** | **1.183 ns** | **1,183 μs** | **0,001183 ms** |

---

## 3. Notas Metodológicas (v4.0)

1. **Clean Start Sincronizado**: O cache foi redefinido via `DimenCache.clearAll()` seguido de um delay de estabilidade (100-200ms) antes da primeira medição.
2. **Warmup Habilitado**: Apesar da limpeza inicial, cada métrica individual possui sua própria fase de Warmup para garantir que a performance de pico seja capturada após o JIT/ART estabilizar.
3. **Persistência Ultra-Rápida**: O carregamento de 100 itens da persistência (DataStore) manteve-se em **~0.4ms**, garantindo inicializações instantâneas.

---
*Relatório de Performance Finalizado (v4.0) · Metodologia Clean Start · Autenticado via Checksum*
