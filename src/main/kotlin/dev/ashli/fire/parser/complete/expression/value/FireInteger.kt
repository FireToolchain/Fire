package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents an Integer primitive in Fire.
 */
class FireInteger(val value: Int) : FireValue() {
    companion object {
        fun parse(tokens: Tokens): FireInteger {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val token = tokens.next()
            if (token.type !is TokenType.Int) throw ParseTokenError("Token is not an integer.", token)
            return FireInteger(token.type.num)
        }

        fun canParse(tokens: Tokens): Boolean {
            if (!tokens.hasNext()) return false
            val token = tokens.peek().type
            return token is TokenType.Int
        }
    }
}