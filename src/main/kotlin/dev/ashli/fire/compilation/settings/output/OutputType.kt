package dev.ashli.fire.compilation.settings.output

/**
 * Represents a Kindling output type.
 */
abstract class OutputType {
    /**
     * Returns null if this OutputType doesn't need any specific kindling compiler flags, otherwise returns a string representing it.
     */
    abstract fun getKindlingFlag(): String?
}