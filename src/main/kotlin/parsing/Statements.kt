package parsing

import structure.ResourceLocation

/**
 * Represents statements that go inside a process or function's codeblock.
 */
sealed interface FireStatement {
    /**
     * Represents a block of code with 0 or more statements inside.
     */
    data class Block(val inner: List<FireStatement>) : FireStatement

    /**
     * Represents a variable definition such as "let x = 5".
     */
    data class DefVar(val def: FireVarDef) : FireStatement

    /**
     * Represents a constant definition such as "let! x = 5".
     */
    data class DefConst(val def: FireConstDef) : FireStatement

    /**
     * Represents a return statement such as "return" or "return 5".
     */
    data class Return(val returnVal: FireExpression?) : FireStatement

    /**
     * Represents inserted kindling code, such as "_kindling '(return (num 5))'".
     */
    data class KindlingInsert(val expr: String) : FireStatement

    /**
     * Represents an if statement such as "if (<cond>) <code>".
     */
    data class If(val condition: FireExpression, val code: FireStatement) : FireStatement

    /**
     * Represents a foreach loop such as "for (a in b) <code>".
     */
    data class For(
        val variable: FireVar.VariableReference,
        val expr: FireExpression,
        val code: FireStatement
    ) : FireStatement

    /**
     * Represents a while loop such as "while (<cond>) <code>".
     */
    data class While(val condition: FireExpression, val code: FireStatement) : FireStatement
    // TODO When/Switch/Match

    /**
     * Represents a call to a process such as "a(b)".
     */
    data class CallProcess(val process: ResourceLocation, val params: List<FireExpression>) : FireStatement

    /**
     * Represents an expression to evaluate.
     */
    data class Expression(val expr: FireExpression) : FireStatement
}