package parsing

import structure.FireResource
import structure.FireType
import structure.ResourceName

/**
 * Represents a variable definition
 */
interface FireVarDef {
    data class Local(val name: ResourceName, val type: FireType) : FireVarDef
    data class Global(val name: ResourceName, val type: FireType) : FireResource(name), FireVarDef
}

/**
 * Represents a constant definition
 */
interface FireConstDef {
    data class Local(val name: ResourceName, val data: FireExpression) : FireVarDef
    data class Global(val name: ResourceName, val data: FireExpression) : FireResource(name), FireVarDef
}


/**
 * Represents a static function.
 * These may be top-level or within a struct.
 */
class FireFunction(
    name: ResourceName,
    val params: List<FireVarDef.Local>,
    val returnType: FireType,
    val code: FireStatement
) : FireResource(name) {

}

/**
 * Represents a method in a structure.
 */
class FireMethod(
    name: ResourceName,
    val mutableThis: Boolean,
    val params: List<FireVarDef.Local>,
    val returnType: FireType,
    val code: FireStatement
) : FireResource(name) {

}

/**
 * Represents a process (they are always static)
 */
class FireProcess(
    name: ResourceName,
    val params: List<FireVarDef.Local>,
    val code: FireStatement
) : FireResource(name) {

}

/**
 * Represents a struct. Contains a map of its fields.
 */
class FireStruct(
    name: ResourceName,
    val fields: Map<ResourceName, FireVarDef.Local>,
    val impls: List<FireTrait>
) : FireResource(name) {

}

/**
 * Represents a trait in fire.
 */
class FireTrait(
    name: ResourceName,
    val impls: List<FireTrait>
) : FireResource(name) {

}