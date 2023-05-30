package structure

/**
 * A "Resource" is something that can be referenced by a resource location string.
 * A resource location string looks like this: A::B::C::D.
 *
 * If you want something referenced like this: A.b.c.d, you are looking for a Property.
 */


/**
 * Represents a resource in fire that may be referenced with a ResourceLocation such as a struct, file, or function.
 */
open class FireResource(val resourceName: ResourceName) {
    var parent: FireResource? = null
    var location = ResourceLocation(listOf(resourceName))
}

/**
 * Represents a resource in fire that contains other resources, such as folders, structs, or enums.
 */
open class FireContainerResource<T : FireResource>(resourceName: ResourceName) : FireResource(resourceName) {
    private val children: MutableMap<ResourceName, T> = mutableMapOf()

    /**
     * Returns a child resource, or null if it doesn't exist.
     */
    fun getChildResourceOrNull(name: ResourceName) = children[name]

    /**
     * Returns a child resource, or throws a NoSuchElementException if it doesn't exist.
     */
    fun getChildResource(name: ResourceName) = getChildResourceOrNull(name) ?: throw NoSuchElementException("No sub-resource named $name")

    /**
     * Follows a ResourceLocation to find a resource.
     * For example: Suppose we have Project::Folder::File::Struct::Method.
     * If we run getInner on the Folder with the Location File::Struct, it will return Struct.
     *
     * May throw NoSuchElementException or IllegalStateException on invalid inputs.
     */
    fun getInner(path: ResourceLocation): FireResource {
        var res: FireResource = this
        for (p in path.dir) {
            if (res is FireContainerResource<*>) {
                res = res.getChildResource(p)
            } else throw IllegalStateException("$resourceName cannot have sub-elements.")
        }
        return res
    }

    /**
     * Adds a resource as a child of this resource.
     * If a child resource with its name already exists, throw IllegalArgumentException.
     */
    fun addChild(resource: T) {
        if (children.contains(resource.resourceName)) throw IllegalArgumentException("Child ${resource.resourceName} already exists")
        children[resource.resourceName] = resource
        resource.parent = this
        resource.location = this.location.child(resource.resourceName)
    }
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
    fun getParentOrNull(): ResourceLocation? {
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
    fun getParent() = getParentOrNull() ?: throw IllegalStateException("ResourceLocation has no parent.")

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
@JvmInline
value class ResourceName(private val name: String) {
    init {
        if (name.isEmpty()) throw IllegalArgumentException("Resource name must have at least one character.")
        val reg = Regex("^[a-zA-Z][a-zA-Z0-9_]*$")
        if (!name.matches(reg)) throw IllegalArgumentException("Resource name must match /^[a-zA-Z][a-zA-Z0-9_]*$/")
    }

    override fun toString() = name
}