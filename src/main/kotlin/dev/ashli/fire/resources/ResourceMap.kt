package dev.ashli.fire.resources

import dev.ashli.fire.parser.Resource

/**
 * Holds Locatable resources in a map format.
 */
class ResourceMap<T : Resource> {
    private val map: MutableMap<ResourceName, T> = mutableMapOf()

    /**
     * Gets a resource by its name. Errors if resource doesn't exist.
     * @param location The resource location.
     * @throws NoSuchElementException If no resource is found.
     * @return The resource denoted by the location.
     */
    operator fun get(location: ResourceName) = map[location] ?: throw NoSuchElementException("No resource with location $location")

    /**
     * Gets a resource by its name. Returns null if the resource doesn't exist.
     *
     * @param location The resource location
     * @return The resource denoted by the location, or null if it doesn't exist.
     */
    fun getOrNull(location: ResourceName) = map[location]

    /**
     * Adds a resource to the map.
     * @param value The value to add.
     */
    fun add(value: T) {
        map[value.name] = value
    }

}