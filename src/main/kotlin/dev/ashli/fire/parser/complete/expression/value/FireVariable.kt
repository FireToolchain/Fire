package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.resources.ResourceName
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a local variable reference in Fire.
 * References to top-level variables are different.
 */
class FireVariable(val name: ResourceName) : FireValue()  {
    companion object {
        fun parse(tokens: Tokens): FireVariable {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val token = tokens.next()
            if (token.type !is TokenType.Ident) throw ParseTokenError("Token is not a variable reference.", token)
            return FireVariable(ResourceName(token.type.str))
        }

        fun canParse(tokens: Tokens): Boolean {
            if (!tokens.hasNext()) return false
            val token = tokens.peek().type
            return token is TokenType.Ident
        }
    }
}