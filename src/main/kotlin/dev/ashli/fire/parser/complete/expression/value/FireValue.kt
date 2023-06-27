package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.complete.expression.Expression
import dev.ashli.fire.parser.error.MissingTokenError
import dev.ashli.fire.parser.error.ParseTokenError
import dev.ashli.fire.tokenizer.TokenType
import dev.ashli.fire.tokenizer.Tokens

abstract class FireValue : Expression() {
    override fun isPure() = true

    companion object {
        fun parse(tokens: Tokens): FireValue {
            if (FireBoolean.canParse(tokens)) return FireBoolean.parse(tokens)
            if (FireInteger.canParse(tokens)) return FireInteger.parse(tokens)
            if (FireNumber.canParse(tokens)) return FireNumber.parse(tokens)
            if (FireString.canParse(tokens)) return FireString.parse(tokens)
            if (This.canParse(tokens)) return This.parse(tokens)
            if (FireVariable.canParse(tokens)) return This.parse(tokens)

            if (!tokens.hasNext()) throw MissingTokenError("No token to parse.", tokens)
            throw ParseTokenError("Token is not a value.", tokens.next())
        }

        fun canParse(tokens: Tokens): Boolean {
            return FireBoolean.canParse(tokens) ||
                    FireInteger.canParse(tokens) ||
                    FireNumber.canParse(tokens) ||
                    FireString.canParse(tokens) ||
                    This.canParse(tokens) ||
                    FireVariable.canParse(tokens)
        }
    }
}