package parsing

import structure.ResourceLocation
import structure.ResourceName


/**
 * Represents an expression node.
 * It does not contain Add, Multiply, GreaterThan, etc. because those are delegated to a CallMethod.
 */
sealed interface FireExpression {
    data class Assign(val a: FireVar, val b: FireExpression) : FireExpression
    data class CallFunc(val func: ResourceLocation, val params: List<FireExpression>) : FireExpression
    data class CallMethod(val method: ResourceName, val caller: FireExpression, val params: List<FireExpression>) : FireExpression
}