package dev.ashli.fire.resources

/**
 * Represents an immutable resource location.
 */
class ResourceLocation(private val location: List<ResourceName>) {
    init {
        if (location.isEmpty()) throw IllegalStateException("ResourceLocation must have at least 1 ResourceName.")
    }

    /**
     * @return True if this ResourceLocation is top-level.
     */
    fun isTopLevel() = location.size == 1

    /**
     * @return True if this ResourceLocation has a parent.
     */
    fun hasParent() = !isTopLevel()

    /**
     * Returns a child resource of this one.
     * For example: (A::B::C).child(D) -> A::B::C::D
     *
     * @param child ResourceLocation or ResourceName of child.
     * @return The child resource location.
     */
    fun child(child: ResourceName) = ResourceLocation(location + child)
    fun child(child: ResourceLocation) = ResourceLocation(location + child.location)

    /**
     * Returns the parent resource of this one.
     * For example: (A::B::C).parent() -> A::B
     *
     * @return The parent resource location.
     * @throws NullPointerException If isTopLevel() is true.
     */
    fun parent() = parentOrNull() ?: throw IllegalStateException("Resource $this has no parent.")

    /**
     * Returns the parent resource of this one.
     * For example: (A::B::C).parent() -> A::B
     *
     * @return The parent resource location, null if resource is top-level.
     */
    fun parentOrNull(): ResourceLocation? {
        return if (isTopLevel()) {
            null
        } else {
            ResourceLocation(location.subList(0, location.size-1))
        }
    }

    /**
     * Gets the name of this location.
     *
     * @return The name of this resource this location points to.
     */
    fun getName() = location[location.size-1]


    override fun toString() = location.joinToString("::")
}