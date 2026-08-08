Sim. Fiz uma auditoria mais ampla da **tag 3.1.6**, comparando também os pontos alterados em relação à 3.1.5. A conclusão é que há problemas reais de correctness, concorrência, lifecycle e medição de performance. Alguns já existiam parcialmente na 3.1.5, mas a 3.1.6 introduziu regressões novas e tornou alguns problemas mais fáceis de acontecer.

E, considerando sua regra de que **cada milissegundo — na verdade, nesse caso cada nanossegundo — conta**, eu **não corrigiria simplesmente colocando `init()` antes de todo bypass**. Isso corrige o resultado, mas pode piorar o hot path. A correção deve atacar correctness **sem penalizar o caminho rápido**.

## Lista de correções para 3.1.7

| #  | Prioridade | Problema                                                   | Status                       | Performance                        |
| -- | ---------- | ---------------------------------------------------------- | ---------------------------- | ---------------------------------- |
| 1  | 🔴 P0      | Bypass executa antes da inicialização dos fatores          | Confirmado                   | Pode explicar os ~30%              |
| 2  | 🔴 P0      | `ScreenFactors` globais não são seguros por janela         | Confirmado arquiteturalmente | Afeta multi-window/foldables       |
| 3  | 🔴 P0      | Cache não inclui identidade da `Configuration`             | Confirmado                   | Pode retornar valor antigo         |
| 4  | 🔴 P0      | Race entre `key` e `value` no cache                        | Confirmado                   | Pode retornar valor de outra chave |
| 5  | 🔴 P0      | Dois writers podem corromper permanentemente um slot       | Confirmado                   | Concorrência                       |
| 6  | 🔴 P0      | Restore publica `key` antes do `value`                     | Confirmado                   | Pode retornar `0f`/valor antigo    |
| 7  | 🔴 P0      | DataStore antigo pode ser restaurado após `invalidate()`   | Confirmado                   | Stale cache                        |
| 8  | 🟠 P1      | `customSensitivityK` perde 16 bits                         | Confirmado                   | Colisões de cache                  |
| 9  | 🟠 P1      | `remember()` passou de 2 para 4 keys                       | Regressão 3.1.6              | Possível alocação por recomposição |
| 10 | 🟠 P1      | Benchmark chama `sdpa` de cache, mas 3.1.6 faz bypass      | Confirmado                   | Métricas ficam inválidas           |
| 11 | 🟠 P1      | Benchmarks não medem as APIs reais suficientemente         | Confirmado                   | Pode esconder regressões           |
| 12 | 🟠 P1      | `WeakHashMap<Context, Activity>` pode manter Activity viva | Confirmado                   | Leak                               |
| 13 | 🟠 P1      | Persistência não possui versão/schema da fórmula           | Confirmado                   | Cache incompatível após update     |
| 14 | 🟠 P1      | Persistência esparsa adicionou boxing/alocações            | Regressão 3.1.6              | GC/cold path                       |
| 15 | 🟠 P1      | `getBatch()` aloca `FloatArray` a cada chamada             | Confirmado                   | Inadequado para ultra-hot path     |
| 16 | 🟡 P2      | R8 rules impedem otimizações demais                        | Confirmado                   | APK/performance                    |
| 17 | 🟡 P2      | Dependências muito maiores que o necessário                | Confirmado                   | APK/build/startup                  |
| 18 | 🟡 P2      | `CI=true` é tratado como JitPack                           | Confirmado                   | Publicação Maven                   |
| 19 | 🟡 P2      | Caminho Dokka usa `\\` de Windows                          | Confirmado                   | Build Linux                        |
| 20 | 🟡 P2      | `shutdown()` não recria automaticamente collector          | Confirmado                   | Persistência                       |
| 21 | 🟡 P2      | Cobertura matemática dos satélites é incompleta            | Confirmado                   | Risco de regressão                 |
| 22 | 🟡 P2      | Modularização foi breaking change em patch version         | Confirmado                   | SemVer/API                         |

Agora os pontos que considero essenciais.

### 1. 🔴 Bypass antes da inicialização

Este é o problema que descobrimos no `.sdp`.

Atualmente:

```text
getOrPut()
  ↓
shouldBypassCache()
  ↓
return compute()
  ↓
DimenCache.init() nunca acontece
```

O código confirma que o bypass ocorre antes do `init(context)`.

Ao mesmo tempo, os fatores começam em `1.0f`, inclusive os novos fatores dos módulos satélite. Por exemplo `DiagonalFactors.scale`, `PowerFactors.scale` e `InterpolatedFactors.scale` começam em `1.0f`.

É exatamente daí que pode surgir:

```text
100.sdp → 100dp
```

quando deveria ser, em `sw=390`:

```text
100 × 390 / 300 = 130dp
```

**Eu não resolveria simplesmente movendo `init()` para antes do bypass**, porque isso adiciona uma leitura volatile/branch ao caminho que deveria ser o mais rápido da biblioteca.

A solução melhor é fazer o fast path simples ser **independente do DimenCache**:

```kotlin
baseValue * configuration.smallestScreenWidthDp * INV_BASE_RATIO
```

Assim o `.sdp` simples fica simultaneamente **correto e potencialmente mais rápido que 3.1.5**.

---

### 2. 🔴 `ScreenFactors` globais não combinam com uma biblioteca baseada em Window

Hoje existe basicamente:

```text
processo Android
        ↓
DimenCache
        ↓
1 ScreenFactors global
```

Mas podem existir:

```text
Activity A → 390dp
Activity B → 600dp
split-screen → 240dp
display externo → 900dp
```

ao mesmo tempo.

Além disso, no `init()` a biblioteca recebe um Context, converte para:

```kotlin
val appContext = context.applicationContext
val config = appContext.resources.configuration
```

e usa essa configuração para os fatores globais.

Isso é especialmente problemático para uma biblioteca que explicitamente trabalha com multi-window.

**Correção recomendada:** cálculos devem ser baseados na `Configuration` da chamada/janela. Application context deve servir para DataStore, não como fonte obrigatória das dimensões da janela.

---

### 3. 🔴 Cache não identifica a configuração da tela

A chave contém:

```text
AR
baseValue
CalcType
ValueType
sensitivity
qualifier
inverter
orientation
ignoreMultiWindow
```

mas não contém:

```text
screenWidthDp
screenHeightDp
smallestWidthDp
densityDpi
fontScale/config generation
```

Isso significa que:

```text
390dp
100.sdp
→ cacheKey X

mudança para 600dp

100.sdp
→ mesmo cacheKey X
```

A biblioteca depende totalmente de `invalidateOnConfigChange()` para evitar o valor antigo.

Só que se a Activity for recriada normalmente, o objeto global `DimenCache` continua vivo no processo e não existe uma garantia automática de invalidação.

Esse problema fica particularmente perigoso para os cálculos que **não fazem bypass**.

### Correção

Eu adicionaria um `configStamp` por entrada de cache.

Não precisa necessariamente aumentar o `Long key`.

Pode existir algo como:

```text
key[]
value[]
configStamp[]
```

e o hit somente é aceito quando:

```text
entry.key == key &&
entry.configStamp == currentConfigStamp
```

Isso acrescenta uma comparação apenas aos caminhos realmente cacheados.

O `.sdp` simples continuaria fora do cache e não pagaria nada.

---

### 4–5. 🔴 O cache atual não é realmente atomicamente collision-safe

Este é um bug importante.

Hoje um miss grava:

```kotlin
shardValues.set(slotIndex, computed.toRawBits())
shardKeys.set(slotIndex, key)
```

São **dois arrays atômicos diferentes**.

Imagine:

```text
slot:
key = A
value = 100
```

Thread 1 quer gravar B:

```text
value = 200
```

mas ainda não trocou `key`.

Thread 2 executa:

```text
key == A   ✓
value == 200
```

e devolve:

```text
A → 200
```

mesmo sendo o valor de B.

Mais grave, dois writers no mesmo slot podem fazer:

```text
Thread A: valueA
Thread B: valueB
Thread B: keyB
Thread A: keyA
```

Resultado final:

```text
keyA + valueB
```

Ou seja: **não é apenas uma janela temporária; um slot pode terminar permanentemente inconsistente.**

Isso contradiz a intenção documentada de cache collision-safe/lock-free.

### Correção focada em performance

Eu não colocaria lock em reads.

Uma alternativa:

```text
READ:
key1 = key
value = value
key2 = key

hit somente se:
key1 == requested &&
key2 == requested
```

e sincronizar **somente writes/misses**, por shard.

Hot cache hit:

```text
sem synchronized
sem mutex
sem allocation
```

O custo extra fica principalmente nos caminhos de cache reais, enquanto `sdp`/percent/density simples continuam bypass.

Outra possibilidade que vale benchmark é `AtomicReferenceArray<CacheEntry>`: uma única publicação atômica resolve key+value, mas precisa medir a indirection/alocação de misses.

---

### 6. 🔴 Restore faz o problema inverso

No restore:

```kotlin
if (shard.keys.compareAndSet(slotIndex, 0L, key)) {
    shard.values.set(slotIndex, value.toRawBits())
}
```

Aqui publica:

```text
key primeiro
value depois
```

Portanto uma thread pode enxergar:

```text
key correta
value = 0/valor anterior
```

O protocolo de publicação precisa ser único para:

```text
normal write
restore
clear
fontScale clear
peek
serialization
```

---

### 7. 🔴 Restore antigo pode ressuscitar cache invalidado

Outro race sério.

`init()`:

```text
captura Config A
↓
inicia leitura assíncrona DataStore
```

Enquanto isso:

```text
Config muda para B
↓
invalidate()
↓
clearAll()
```

Mas o coroutine antigo pode terminar depois:

```text
DataStore A termina
↓
loadFromByteArray(A)
```

Não existe validação da `persistenceGeneration` antes do `loadFromByteArray()`.

Resultado:

> cache A pode voltar depois que B já invalidou tudo.

### Correção

Capturar:

```kotlin
val generationAtStart = persistenceGeneration.get()
```

e antes de restaurar:

```kotlin
if (generationAtStart != persistenceGeneration.get()) return
```

Além de validar novamente SW/dpi/configuração atual.

Isso custa **zero no hot path**.

---

### 8. 🟠 `customSensitivityK` não é representado exatamente

Hoje:

```kotlin
customSensitivityK
    ?.toRawBits()
    ?.ushr(16)
    ?.and(0xFFFF)
```

Só metade dos 32 bits do `Float` entra na chave.

Portanto dois valores diferentes podem produzir a mesma key.

Exemplo de faixa real: vários floats entre aproximadamente:

```text
0.0026550 ...
0.0026702 ...
```

compartilham o mesmo prefixo `0x3B2E`.

Se uma entrada estiver cacheada com K1 e o consumidor pedir K2, existe possibilidade de receber K1.

### Melhor solução para performance

Não aumentaria simplesmente a chave.

Para vários cálculos, o componente caro é independente de K. Então:

```text
cache:
base scale

fora do cache:
× custom AR factor
```

Ou, sendo um recurso avançado e raro, bypass completo para `customSensitivityK != null`, desde que o benchmark mostre que é mais rápido.

---

### 9. 🟠 Regressão Compose: `remember` passou de 2 para 4 keys

3.1.5:

```kotlin
remember(cacheKey, layoutStamp) {
    ...
}
```

3.1.6:

```kotlin
remember(match, cacheKey, layoutStamp, passthrough) {
    ...
}
```

Isso foi feito para corrigir estabilidade de slots nas APIs `*Plain`, o que é válido.

Mas o caminho normal também paga por:

```text
match
passthrough
```

mesmo sendo sempre:

```text
true
Dp.Unspecified
```

E quatro keys podem cair no overload `vararg` do Compose, criando array/mais comparações por recomposição.

### Melhor solução

Ter dois caminhos:

```kotlin
// ultra-fast normal
rememberDimenDp(cacheKey, layoutStamp)

// condicional, apenas Plain
rememberDimenDpConditional(
    cacheKey,
    layoutStamp,
    match,
    passthrough
)
```

Assim `.sdp`, `.hdp`, `.wdp` recuperam exatamente o caminho enxuto da 3.1.5.

Esse é um dos primeiros pontos que eu benchmarkaria.

---

### 10. 🟠 O benchmark chama `sdpa` de "cache", mas na 3.1.6 ele faz bypass

O novo `shouldBypassCache()` explicitamente permite bypass do caminho AR padrão quando:

```text
SMALL_WIDTH
DEFAULT
customSensitivityK == null
```

Mas o benchmark atual ainda mede:

```kotlin
ops.sdpa(context, 40)
```

e registra:

```text
sdpa (cache)
```

Na 3.1.6 isso **não é mais um teste real de cache hit**.

Portanto números publicados como:

```text
sdpa cache ≈ 35 ns
```

precisam ser revalidados. O relatório ainda descreve `sdpa` como cache.

Para medir cache de verdade use, por exemplo, um path propositalmente não-bypass.

---

### 11. 🟠 Os benchmarks atuais não protegem a performance da biblioteca

O `DimenPerformanceTest` faz muitos testes assim:

```kotlin
DimenCache.getOrPut(key) { 0f }
```

Ou seja, mede:

```text
DimenCache
```

e não necessariamente:

```text
100.sdp
100.hdp
Compose
LocalConfiguration
buildKey
remember
Dp conversion
px conversion
module dispatch
```

Além disso, reporta o **menor** resultado dos trials:

```text
min(trials)
```

que tende a apresentar o cenário mais otimista.

E o Android test repete o mesmo padrão.

### Para 3.1.7 eu colocaria um gate obrigatório

```text
3.1.5       3.1.7
------       -----
1.sdp   vs  1.sdp
100.sdp vs  100.sdp

Compose
Code/View
DP
PX
SP

cold
warm
hot

AR
no AR

390dp
600dp
840dp

portrait
landscape
multi-window
```

E a regra:

```text
resultado matemático:
0 regressões

hot path:
3.1.7 <= 3.1.5

alocações:
3.1.7 <= 3.1.5
```

Para operações abaixo de ~20ns eu usaria também limite absoluto, não apenas porcentagem, porque ruído de 1ns já representa 5–50%.

---

### 12. 🟠 Cache `Context → Activity` pode causar leak

Existe:

```kotlin
WeakHashMap<Context, Activity?>
```

O problema é que quando:

```text
key = Activity
value = mesma Activity
```

a key é weak, mas o **value mantém a Activity fortemente viva**.

Então o valor pode impedir justamente a key de ser coletada.

Trocar por:

```kotlin
WeakHashMap<Context, WeakReference<Activity>>
```

ou eliminar esse cache.

E há outro detalhe de performance: o mapa é sincronizado, então variantes `*i` podem tocar `synchronized` durante resolução.

---

### 13. 🟠 Falta uma versão de schema no cache persistido

DataStore atualmente valida principalmente SW e DPI.

Não existe algo equivalente a:

```text
CACHE_SCHEMA_VERSION = 4
FORMULA_VERSION = ...
```

Se uma versão futura alterar:

```text
fórmula
bits da key
CalcType
ValueType
sensibilidade
```

uma entrada antiga pode continuar sendo interpretada pela nova.

Adicionar:

```text
KEY_CACHE_SCHEMA
```

e zerar automaticamente em mismatch.

Custo no hot path: **zero**.

---

### 14. 🟠 A persistência 3.1.6 adiciona muita alocação desnecessária

O novo serializer usa:

```kotlin
ArrayList<Long>
ArrayList<Int>
```

Logo existe boxing:

```text
Long → java.lang.Long
Int → java.lang.Integer
```

para centenas/milhares de slots.

O loader também usa:

```kotlin
Pair<Int, Int>
```

por entrada através de `shardAndSlot()`.

Mesmo rodando em IO, GC pertence ao processo e pode impactar UI.

Eu substituiria por buffer primitivo único:

```text
ByteArray / ByteBuffer
```

sem ArrayList/boxing.

A 3.1.5 era mais simples nesse aspecto.

---

### 15. 🟠 `getBatch()` não deveria obrigatoriamente alocar

Hoje:

```kotlin
val results = FloatArray(size)
```

em toda chamada.

Para um método vendido como ultra-performance, adicionaria:

```kotlin
getBatch(
    keys: LongArray,
    destination: FloatArray,
    ...
)
```

mantendo o atual como conveniência.

Então aplicações realmente hot podem reutilizar:

```text
LongArray
FloatArray
```

e trabalhar com **0 allocations**.

---

### 16. 🟡 R8 está impedindo otimizações demais

Há regras como:

```proguard
-keep class com.appdimens.dynamic.core.DimenCache { *; }
-keep class kotlin.jvm.internal.** { *; }
-keep class androidx.datastore.** { *; }
```

e keeps amplos para APIs Compose/code.

Especialmente:

```proguard
-keep class kotlin.jvm.internal.** { *; }
-keep class androidx.datastore.** { *; }
```

são excessivos.

A justificativa no próprio arquivo diz que DataStore depende do nome da classe para determinar o arquivo, mas o código define explicitamente:

```kotlin
preferencesDataStore(
    name = "com.appdimens.dynamic.cache"
)
```

Portanto essa justificativa não se aplica.

Além disso cada satélite adiciona novos `-keep` sobre seu pacote inteiro.

Para uma biblioteca em que nanossegundos contam, deve-se permitir que R8:

```text
inline
merge
remove
propagate constants
elimine código morto
```

sempre que possível.

---

### 17. 🟡 Dependências muito pesadas no core e satélites

O artefato principal inclui, entre outras:

```text
WindowManager
Activity Compose
Material3
Material
DataStore
UI Graphics
Tooling Preview
```

E satélites repetem praticamente o mesmo conjunto.

Muitas estratégias matemáticas não precisam de Material3, por exemplo.

Eu reduziria para dependências realmente usadas.

Isso melhora:

```text
dependency graph
tempo de build
APK debug
R8 workload
DEX
consumo de memória do build
```

---

### 18. 🟡 `CI=true` está sendo confundido com JitPack

Existe:

```kotlin
val isJitPack =
    JITPACK == true ||
    jitpack == true ||
    CI == true ||
    ci == true
```

e depois:

```kotlin
if (!isJitPack) {
    publishToMavenCentral()
    signAllPublications()
}
```

Isso aparece também nos módulos.

Em GitHub Actions normalmente:

```text
CI=true
```

Então GitHub Actions pode ser interpretado como JitPack e não configurar Central/signing.

Use somente uma variável explicitamente JitPack.

---

### 19. 🟡 Dokka ainda tem caminho Windows

Na própria tag 3.1.6:

```kotlin
outputDirectory.set(
    layout.projectDirectory.dir("${rootDir}\\DOCUMENTATION2")
)
```

Isso deveria ser:

```kotlin
rootProject.layout.projectDirectory.dir("DOCUMENTATION2")
```

ou API equivalente de `layout`.

---

### 20. 🟡 `shutdown()` não cumpre completamente a documentação

`shutdown()` coloca:

```kotlin
_scope = null
```

mas `saveToPersistence()` apenas:

```kotlin
saveFlow.tryEmit(context)
```

Não toca no getter `scope`, portanto não recria necessariamente o collector, embora o comentário diga que ele seria automaticamente recriado no próximo uso.

É principalmente problema de teste/diagnóstico, mas é uma inconsistência real.

---

### 21. 🟡 Falta cobertura das estratégias depois da modularização

A busca na 3.1.6 encontra testes específicos de fórmula para:

```text
Auto
Percent
Diagonal
```

Mas a 3.1.6 modularizou muito mais estratégias.

Eu criaria uma mesma suíte parametrizada para **todas**:

```text
Scaled
Auto
Percent
Power
Fluid
Diagonal
Fill
Fit
Interpolated
Logarithmic
Perimeter
Density
Resize
Units
```

testando obrigatoriamente:

```text
1
16
100
-1
0
Float
Int

300dp
360dp
390dp
411dp
600dp
840dp

AR ligado/desligado
custom K
rotation
multi-window
Code vs Compose
```

---

### 22. 🟡 3.1.6 é breaking change em versão patch

A documentação da própria 3.1.6 diz que:

```text
3.1.5:
uma dependência contém todas estratégias

3.1.6:
principal + módulos satélite
```

Tecnicamente isso é breaking para quem apenas troca:

```text
3.1.5 → 3.1.6
```

e usava uma estratégia retirada do artefato principal.

Eu manteria a modularização, mas semanticamente uma mudança dessas seria mais apropriada para uma minor/major conforme o contrato adotado.

---

# Como eu estruturaria a correção sem perder performance

Eu faria a **3.1.7** em três fases.

**Fase A — correctness P0**, antes de qualquer nova otimização: eliminar dependência de fatores globais no fast bypass; tornar cache config-aware; corrigir publicação atômica key/value; proteger restore com generation; corrigir restore concorrente. Não publicar enquanto testes concorrentes não passarem.

**Fase B — recuperar e superar 3.1.5:** restaurar o `remember(cacheKey, stamp)` de duas keys no caminho comum; criar fast paths especializados que nem constroem cache key para fórmulas mais baratas que o cache; remover boxing da persistência; adicionar `getBatch(..., destination)`; reduzir R8 keeps.

**Fase C — performance gate:** benchmarkar **3.1.5 e 3.1.7 lado a lado no mesmo APK/aparelho**, não comparar números antigos de documentos. O requisito deve ser:

```text
CORRECTNESS
3.1.7 == valor matemático esperado → obrigatório

HOT PATH
3.1.7 <= 3.1.5 ns/op

ALLOCATIONS
3.1.7 <= 3.1.5

COLD PATH
3.1.7 <= 3.1.5 ou diferença justificada

COMPOSE RECOMPOSITION
3.1.7 <= 3.1.5

APK/R8
3.1.7 <= 3.1.5 considerando módulos equivalentes
```

A minha prioridade seria **não aplicar ainda o patch simples “move `init()` acima do bypass” isoladamente**. Ele corrige o bug dos 30%, mas coloca trabalho adicional justamente no caminho de ~2ns que você quer preservar. Dá para corrigir o problema e simultaneamente deixar o `.sdp` simples **mais rápido** eliminando cache/init desse caminho por completo.

**Minha avaliação da 3.1.6 neste momento:** eu não a consideraria uma base final confiável para publicar como versão estável de referência. A modularização é boa, e várias alterações de arquitetura são interessantes, mas eu faria uma **3.1.7 focada exclusivamente em correctness + performance**, sem adicionar features, e só depois seguiria evoluindo as estratégias.

Se quiser, no próximo passo eu posso pegar **essa lista P0→P2 e transformar em um plano técnico de implementação arquivo por arquivo**, indicando exatamente **qual função alterar, código atual, código proposto e teste/benchmark necessário para cada correção**.
