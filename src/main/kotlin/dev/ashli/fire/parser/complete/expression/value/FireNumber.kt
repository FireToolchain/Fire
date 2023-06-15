package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a Number primitive in Fire.
 */
class FireNumber(val value: Float) : FireValue() {
    companion object {
        fun parse(tokens: Tokens): FireNumber {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val token = tokens.next()
            return when (token.type) {
                is TokenType.Int -> FireNumber(token.type.num.toFloat())
                is TokenType.Num -> FireNumber(token.type.num)
                else -> throw ParseTokenError("Token is not a number.", token)
            }
        }
    }
}