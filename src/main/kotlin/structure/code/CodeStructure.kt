package structure.code

import structure.*

/**
 * Represents a fire struct. It contains methods. Its properties
 */
class FireStruct(name: ResourceName) : FireContainerResource<FireResource>(name), PropertyHolder<FireType>, FireType,
    GenericHolder {
    override val props = mutableMapOf<ResourceName, FireType>()
    override val generics = mutableMapOf<ResourceName, GenericType>()
    override val supertypes = mutableMapOf<ResourceName, FireType>()
}

// TODO Enums

// TODO Traits

/**
 * Represents a fire top-level function.
 */
class FireFunction(name: ResourceName, val params: List<FireVariable>, val returnType: FireType) : FireResource(name) {

}

/**
 * Represents a fire method.
 */
class FireMethod(name: ResourceName, val params: List<FireVariable>, val returnType: FireType) : FireResource(name) {

}

/**
 * Represents a fire local variable.
 */
class FireVariable(val name: ResourceName, val type: FireType) {

}

/**
 * Represents a fire global variable.
 */
class FireTopLevelVariable(name: ResourceName, val type: FireType) : FireResource(name) {

}