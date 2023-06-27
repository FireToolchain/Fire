package dev.ashli.fire.parser.complete.expression.calculations

import dev.ashli.fire.parser.complete.expression.Expression
import dev.ashli.fire.parser.complete.expression.value.FireVariable
import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents an Assignment expression.
 */
class Assignment(val variable: FireVariable, val value: Expression) : Expression() {
    override fun isPure() = false

    companion object {
        fun parse(tokens: Tokens): Assignment {
            val lhs = FireVariable.parse(tokens)
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val assignToken = tokens.next()
            val rhs = Expression.parse(tokens)

            return when (assignToken.type) {
                is TokenType.Assign -> Assignment(lhs, rhs)
                else -> throw ParseTokenError("Token is not a valid assignment operator", assignToken)

                // TODO Other types (+=, -=, etc)
            }
        }

        // TODO not exactly complete
        fun canParse(tokens: Tokens): Boolean {
            if (!FireVariable.canParse(tokens)) return false
            return tokens.peek(1).type is TokenType.Assign
        }
    }
}