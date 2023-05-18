package structure

interface FireType {
    val supertypes: Map<ResourceName, FireType>
}

interface GenericHolder {
    val generics: MutableMap<ResourceName, GenericType>

    /**
     * Returns a generic's restrictions, or null if it doesn't exist.
     */
    fun getTypeOrNull(name: ResourceName) = generics[name]

    /**
     * Returns a property, or throws a NoSuchElementException if it doesn't exist.
     */
    fun getType(name: ResourceName) = getTypeOrNull(name) ?: throw NoSuchElementException("No generic named $name")

    /**
     * Adds a property to this resource.
     * If a property with this name already exists, throw IllegalArgumentException.
     */
    fun addType(name: ResourceName, type: GenericType) {
        if (generics.contains(name)) throw IllegalArgumentException("Type $name already exists")
        generics[name] = type
    }
}

class GenericType(val name: ResourceName) : FireType {
    override val supertypes = mutableMapOf<ResourceName, FireType>()
}