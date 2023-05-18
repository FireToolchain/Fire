package structure

/**
 * Represents that the object is a Fire Resource, such as a folder, file, struct, enum, function etc.
 */
interface FireResource {
    val location: ResourceLocation
    val resourceName: ResourceName
}

/**
 * Represents a pointer to a resource location; immutable.
 *
 * Input list length > 0, otherwise IllegalStateException.
 */
data class ResourceLocation(val dir: List<ResourceName>) {
    init {
        if (dir.isEmpty()) throw IllegalStateException("ResourceLocation must have one node.")
    }

    /**
     * Return a ResourceLocation with a new child node.
     * A::B::C with child D returns A::B::C::D
     */
    fun child(s: ResourceName): ResourceLocation {
        val clone = mutableListOf<ResourceName>()
        clone.addAll(dir)
        clone.add(s)
        return ResourceLocation(clone)
    }

    /**
     * Returns a ResourceLocation that goes "out of" the current resource.
     * A::B::C's parent is A::B
     *
     * A resource with one node will return null. (A) has no parent.
     */
    fun parentOrNull(): ResourceLocation? {
        return if (dir.size > 2) {
            ResourceLocation(dir.subList(0, dir.size-1))
        } else null
    }

    /**
     * Returns a ResourceLocation that goes "out of" the current resource.
     * A::B::C's parent is A::B
     *
     * A resource with one node will throw an IllegalStateException. (A) has no parent.
     */
    fun parent() = parentOrNull() ?: throw IllegalStateException("ResourceLocation has no parent.")

    /**
     * Returns the head element of the ResourceLocation.
     * A::B::C's head is C.
     */
    fun head() = dir.last()

    override fun toString() = dir.joinToString("::")
}

/**
 * Represents the name of a resource.
 * Input string must match /^[a-zA-Z][a-zA-Z0-9_]*$/ or an IllegalArgumentException is thrown.
 * Immutable.
 */
data class ResourceName(val name: String) {
    init {
        if (name.isEmpty()) throw IllegalArgumentException("Resource name must have at least one character.")
        val reg = Regex("^[a-zA-Z][a-zA-Z0-9_]*$")
        if (!name.matches(reg)) throw IllegalArgumentException("Resource name must match /^[a-zA-Z][a-zA-Z0-9_]*$/")
    }

    override fun toString() = name
}