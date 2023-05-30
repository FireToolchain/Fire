package dev.ashli.fire.parser.type

import dev.ashli.fire.resources.ResourceName

/**
 * A generic type.
 */
data class GenericType(val name: ResourceName, val constraints: Set<Type>) : Type() {
    override fun conformsTo(other: Type): Boolean {
        TODO()
    }
}