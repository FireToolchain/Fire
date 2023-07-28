package dev.ashli.fire.parser.complete.statement

import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a literal kindling statement.
 * For example:
 * __kindling "(set-var '=' ((var 'numberVar')(num 2.5)))"
 */
class Kindling(val kindling: String) {
    companion object {
        fun parse(tokens: Tokens): Kindling {
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val identToken = tokens.next()
            if (identToken.type !is TokenType.Ident) throw ParseTokenError("Token is not a 'kindling' keyword.", identToken)
            if (identToken.type.str != "__kindling") throw ParseTokenError("Token is not a 'kindling' keyword.", identToken)
            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            val stringToken = tokens.next()
            if (stringToken.type !is TokenType.String) throw ParseTokenError("Token is not a string.", stringToken)
            return Kindling(stringToken.type.str)
        }

        fun canParse(tokens: Tokens): Boolean {
            if (!tokens.hasNext()) return false
            val identToken = tokens.peek().type
            if(identToken !is TokenType.Ident) return false
            if(identToken.str != "__kindling") return false
            val stringToken = tokens.peek(1).type
            return stringToken is TokenType.String
        }
    }
}