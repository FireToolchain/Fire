package dev.ashli.fire.parser.definitions

import dev.ashli.fire.parser.Resource
import dev.ashli.fire.parser.TopLevelHolder
import dev.ashli.fire.parser.annotations.Annotation
import dev.ashli.fire.resources.ResourceLocation

/**
 * Represents a Fire enum.
 */
class Enum(location: ResourceLocation, annotations: List<Annotation>) :
    TopLevelHolder(location, annotations) {
}