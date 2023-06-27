package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a Boolean primitive in Fire.
 */
class FireBoolean(val value: kotlin.Boolean) : FireValue() {
    companion object {
        fun parse(tokens: Tokens): FireBoolean {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val token = tokens.next()
            return when (token.type) {
                is TokenType.True -> FireBoolean(true)
                is TokenType.False -> FireBoolean(false)
                else -> throw ParseTokenError("Token is not a boolean.", token)
            }
        }

        fun canParse(tokens: Tokens): Boolean {
            if (!tokens.hasNext()) return false
            val token = tokens.peek().type
            return token is TokenType.True || token is TokenType.False
        }
    }
}