package dev.ashli.fire.compilation.settings.output

/**
 * Outputs Fire through recode.
 */
class RecodeOutput : OutputType() {
    override fun getKindlingFlag() = "--recode"
}