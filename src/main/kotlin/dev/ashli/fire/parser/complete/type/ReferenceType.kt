package dev.ashli.fire.parser.complete.type

import dev.ashli.fire.resources.ResourceLocation

/**
 * A type that is a reference to an existing Struct, Enum, or Trait.
 */
data class ReferenceType(val location: ResourceLocation) : Type() {
    override fun conformsTo(other: Type): Boolean {
        TODO()
    }
}