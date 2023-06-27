package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a String primitive in Fire.
 */
class FireString(val value: String) : FireValue()  {
    companion object {
        fun parse(tokens: Tokens): FireString {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val token = tokens.next()
            if (token.type !is TokenType.String) throw ParseTokenError("Token is not a string.", token)
            return FireString(token.type.str)
        }

        fun canParse(tokens: Tokens): Boolean {
            if (!tokens.hasNext()) return false
            val token = tokens.peek().type
            return token is TokenType.String
        }
    }
}