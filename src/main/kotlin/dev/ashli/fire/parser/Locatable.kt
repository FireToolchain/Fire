package dev.ashli.fire.parser

import dev.ashli.fire.resources.ResourceLocation

/**
 * Represents that a parsed structure is locatable using a ResourceLocation.
 */
interface Locatable {
    val location: ResourceLocation
}