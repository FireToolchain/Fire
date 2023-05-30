package parsing

import structure.ResourceLocation
import structure.ResourceName

/**
 * Represents a variable in code. Either local "m" or global, with::a::resource::location.
 */
sealed interface FireVar : FireValue {
    data class VariableReference(val name: ResourceName) : FireVar
    data class GlobalVariableReference(val name: ResourceLocation) : FireVar
}

/**
 * Represents a value in code.
 */
sealed interface FireValue : FireExpression {
    data class Int(val num: kotlin.Int) : FireValue
    data class Num(val num: Float) : FireValue
    data class String(val str: kotlin.String) : FireValue
    data class Boolean(val bool: kotlin.Boolean) : FireValue
}
