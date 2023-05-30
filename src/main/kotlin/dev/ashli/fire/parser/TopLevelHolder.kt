package dev.ashli.fire.parser

import dev.ashli.fire.parser.annotations.Annotated
import dev.ashli.fire.parser.annotations.Annotation
import dev.ashli.fire.resources.ResourceLocation
import dev.ashli.fire.resources.ResourceMap
import dev.ashli.fire.resources.ResourceName

/**
 * Denotes that a parsed Fire structure is top-level, and holds resources within it.
 */
open class TopLevelHolder(
    location: ResourceLocation,
    annotations: List<Annotation>,
) : TopLevel(location, annotations), Annotated {
    private val children = ResourceMap<Resource>()

    /**
     * Gets a child of this resource.
     * @param name The name of the child resource.
     * @return The child element
     * @throws NoSuchElementException If resource does not exist.
     */
    fun getChild(name: ResourceName) = children[name]


    /**
     * Gets a child of this resource, or null if it doesn't exist.
     * @param name The name of the child resource.
     * @return The child element, or null if doesn't exist.
     */
    fun getChildOrNull(name: ResourceName) = children.getOrNull(name)
}
