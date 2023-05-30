package dev.ashli.fire.tokenizer

/**
 * Represents an error during Tokenization.
 */
class SyntaxError(str: String, line: Int, col: Int) : Error("$str\n At: line $line, column $col.")