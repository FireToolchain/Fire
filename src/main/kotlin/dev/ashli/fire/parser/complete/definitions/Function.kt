package dev.ashli.fire.parser.complete.definitions

import dev.ashli.fire.parser.TopLevel
import dev.ashli.fire.parser.complete.annotations.Annotation
import dev.ashli.fire.resources.ResourceLocation

/**
 * Represents a Fire top-level function
 */
class Function(location: ResourceLocation, annotations: List<Annotation>) :
    TopLevel(location, annotations) {
}