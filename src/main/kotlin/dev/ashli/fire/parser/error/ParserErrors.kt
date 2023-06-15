package dev.ashli.fire.parser.error

import dev.ashli.fire.tokenizer.Token
import dev.ashli.fire.tokenizer.Tokens

open class ParseError(s: String) : Exception(s)

class ParseTokenError(s: String, t: Token) : ParseError("$s\n ${t.position}")

class MissingTokenError(s: String, t: Tokens) : ParseError("$s\n ${t.position()}")