################################################################################
# AppDimens Dynamic — proguard-rules.pro (library module)
#
# The :library AAR builds with minifyEnabled = false (see build.gradle.kts),
# so these rules are DEAD CODE for module builds: R8/ProGuard does not run on
# this module at all.
#
# The rules that actually protect the shipped artifact live in
# consumer-rules.pro — they are bundled inside the AAR and merged into the
# consuming app's R8 run.
#
# If this comment block changes (e.g. minifyEnabled becomes true here), keep
# this file in sync with consumer-rules.pro: the app's build uses the consumer
# rules, this module's own build uses this file. Duplicating keeps in both is
# NOT a correctness problem (keeps are idempotent), but it is dead weight.
################################################################################