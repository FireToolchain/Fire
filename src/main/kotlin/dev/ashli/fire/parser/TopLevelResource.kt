package dev.ashli.fire.parser

import dev.ashli.fire.parser.complete.annotations.Annotated
import dev.ashli.fire.parser.complete.annotations.Annotation
import dev.ashli.fire.resources.ResourceLocation

/**
 * Denotes that a parsed Fire structure is top-level.
 */
open class TopLevelResource(
    override val location: ResourceLocation,
    override val annotations: List<Annotation>
) : Resource, Annotated {

}
