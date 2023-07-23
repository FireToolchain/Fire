package dev.ashli.fire.parser.complete.statement

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a continue statement.
 */
class Continue {
    companion object {
        fun parse(tokens: Tokens): Continue {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val token = tokens.next()
            if (token.type !is TokenType.Continue) throw ParseTokenError("Token is not a 'continue' keyword.", token)
            return Continue()
        }

        fun canParse(tokens: Tokens): Boolean {
            if (!tokens.hasNext()) return false
            val token = tokens.peek().type
            return token is TokenType.Continue
        }
    }
}