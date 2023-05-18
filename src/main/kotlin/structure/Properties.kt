package structure

/**
 * Mark this resource as having properties.
 * A property is
 */
interface PropertyHolder<T> {
    val props: MutableMap<ResourceName, T>

    /**
     * Returns a property, or null if it doesn't exist.
     */
    fun getPropertyOrNull(name: ResourceName) = props[name]

    /**
     * Returns a property, or throws a NoSuchElementException if it doesn't exist.
     */
    fun getProperty(name: ResourceName) = getPropertyOrNull(name) ?: throw NoSuchElementException("No property named $name")

    /**
     * Adds a property to this resource.
     * If a property with this name already exists, throw IllegalArgumentException.
     */
    fun addProperty(name: ResourceName, type: T) {
        if (props.contains(name)) throw IllegalArgumentException("Child $name already exists")
        props[name] = type
    }
}

