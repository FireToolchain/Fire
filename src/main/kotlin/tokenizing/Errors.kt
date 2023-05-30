package tokenizing

/**
 * Represents an error that occurs during tokenization.
 */
class SyntaxError(str: String, line: Int, col: Int) : Error("$str\n At: line $line, column $col.")