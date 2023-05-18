package parsing

import structure.ResourceLocation

/**
 * Represents statements that go inside a process or function's codeblock.
 */
sealed interface FireStatement {
    data class Block(val inner: List<FireStatement>) : FireStatement
    data class Return(val returnVal: FireExpression) : FireStatement
    data class KindlingInsert(val expr: String) : FireStatement
    data class If(val condition: FireExpression, val code: FireStatement) : FireStatement
    data class For(
        val variable: FireVar.VariableReference,
        val expr: FireExpression,
        val code: FireStatement
    ) : FireStatement
    data class While(val condition: FireExpression, val code: FireStatement) : FireStatement
    // TODO When/Switch/Match
    data class CallProcess(val process: ResourceLocation, val params: List<FireExpression>) : FireStatement
}