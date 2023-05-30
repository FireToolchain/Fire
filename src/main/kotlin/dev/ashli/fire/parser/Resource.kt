package dev.ashli.fire.parser

import dev.ashli.fire.resources.ResourceLocation
import dev.ashli.fire.resources.ResourceName

/**
 * Represents that a parsed structure is locatable using a ResourceLocation.
 */
interface Resource {
    val location: ResourceLocation
    val name: ResourceName
        get() = location.getName()
}