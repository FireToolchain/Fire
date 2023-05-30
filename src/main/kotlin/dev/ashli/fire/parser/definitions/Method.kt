package dev.ashli.fire.parser.definitions

import dev.ashli.fire.parser.Resource
import dev.ashli.fire.parser.annotations.Annotated
import dev.ashli.fire.parser.annotations.Annotation
import dev.ashli.fire.resources.ResourceLocation

/**
 * Represents a Fire method (function inside of an enum, struct, trait, etc)
 */
class Method(
    override val location: ResourceLocation,
    override val annotations: List<Annotation>
) : Resource, Annotated {
}