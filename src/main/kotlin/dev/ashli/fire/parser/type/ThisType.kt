package dev.ashli.fire.parser.type

import dev.ashli.fire.resources.ResourceLocation

/**
 * A type that represents This.
 */
data class ThisType(val location: ResourceLocation) : Type() {
    override fun conformsTo(other: Type): Boolean {
        TODO()
    }
}