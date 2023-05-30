package dev.ashli.fire.parser

import dev.ashli.fire.parser.annotations.Annotated
import dev.ashli.fire.parser.annotations.Annotation
import dev.ashli.fire.resources.ResourceLocation

/**
 * Denotes that a parsed Fire structure is top-level.
 */
open class TopLevel(override val location: ResourceLocation, override val annotations: List<Annotation>) : Locatable, Annotated
