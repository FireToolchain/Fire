package dev.ashli.fire.tokenizer

/**
 * Represents a list of tokens.
 */
data class Tokens(val list: List<Token>)

/**
 * Represents a single token. Includes its position, width, and type-data.
 */
data class Token(val type: TokenType, val line: Int, val column: Int, val width: Int)

/**
 * Represents a token type.
 */
sealed interface TokenType {
    // Grouping symbols
    data object OpenParen : TokenType
    data object CloseParen : TokenType
    data object OpenBracket : TokenType
    data object CloseBracket : TokenType
    data object OpenBrace : TokenType
    data object CloseBrace : TokenType

    // Operators
    data object LessThan : TokenType
    data object GreaterThan : TokenType
    data object GreaterEqual : TokenType
    data object LessEqual : TokenType
    data object NotEqual : TokenType
    data object Equal : TokenType
    data object Plus : TokenType
    data object Minus : TokenType
    data object Multiply : TokenType
    data object Divide : TokenType
    data object Remainder : TokenType
    data object Concat : TokenType
    data object BoolOr : TokenType
    data object BoolAnd : TokenType
    data object BoolNot : TokenType
    data object Arrow : TokenType


    // Structure
    data object Assign : TokenType
    data object PlusAssign : TokenType
    data object MinusAssign : TokenType
    data object MultiplyAssign : TokenType
    data object DivideAssign : TokenType
    data object RemainderAssign : TokenType
    data object ConcatAssign : TokenType
    data object Colon : TokenType
    data object Comma : TokenType
    data object Dot : TokenType
    data object Semicolon : TokenType
    data class Annotation(val str: kotlin.String) : TokenType

    // Keywords
    data object MutableLet : TokenType
    data object ImmutableLet : TokenType
    data object Function : TokenType
    data object Process : TokenType
    data object If : TokenType
    data object Else : TokenType
    data object Return : TokenType
    data object Struct : TokenType
    data object Private : TokenType
    data object Impl : TokenType
    data object For : TokenType
    data object While : TokenType
    data object Enum : TokenType
    data object Trait : TokenType
    data object Import : TokenType
    data object Where : TokenType


    // Values
    data class Ident(val str: kotlin.String) : TokenType
    data class Int(val num: kotlin.Int) : TokenType
    data class Num(val num: Float) : TokenType
    data class String(val str: kotlin.String) : TokenType
    data object True : TokenType
    data object False : TokenType

}

/**
 * Tokenizes a string into a Tokens object.
 *
 * @param str String to tokenize
 * @return Tokens
 *
 * @throws SyntaxError
 */
fun tokenize(str: String): Tokens {
    val list = mutableListOf<Token>()
    var line = 1
    var column = 0
    var index = 0
    while (index < str.length) {
        val char = str[index]
        when {
            char == '(' -> list.add(Token(TokenType.OpenParen, line, column, 1))
            char == ')' -> list.add(Token(TokenType.CloseParen, line, column, 1))
            char == '[' -> list.add(Token(TokenType.OpenBracket, line, column, 1))
            char == ']' -> list.add(Token(TokenType.CloseBracket, line, column, 1))
            char == '{' -> list.add(Token(TokenType.OpenBrace, line, column, 1))
            char == '}' -> list.add(Token(TokenType.CloseBrace, line, column, 1))
            char == ',' -> list.add(Token(TokenType.Comma, line, column, 1))
            char == '.' -> list.add(Token(TokenType.Dot, line, column, 1))
            char == ';' -> list.add(Token(TokenType.Semicolon, line, column, 1))
            char == ':' -> list.add(Token(TokenType.Colon, line, column, 1))
            char == '+' -> if (index + 1 < str.length && str[index + 1] == '=') {
                list.add(Token(TokenType.PlusAssign, line, column, 2))
                index+=2; column+=2
                continue
            } else list.add(Token(TokenType.Plus, line, column, 1))
            char == '-' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.MinusAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '>' -> {
                        list.add(Token(TokenType.Arrow, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Minus, line, column, 1))
                }
            } else list.add(Token(TokenType.Minus, line, column, 1))
            char == '*' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.MultiplyAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Multiply, line, column, 1))
                }
            } else list.add(Token(TokenType.Multiply, line, column, 1))
            char == '/' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.DivideAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '/' -> {
                        while (index < str.length && str[index] != '\n') index++
                    }
                    '*' -> {
                        index++; column++
                        while (index < str.length) {
                            if (str[index] == '*' && index + 1 < str.length && str[index+1] == '/') {
                                index += 2
                                column += 2
                                break
                            } else if (str[index] == '\n') {
                                index++
                                column = 0
                                line++
                            } else {
                                index++; column++
                            }
                        }
                        continue
                    }
                    else -> list.add(Token(TokenType.Divide, line, column, 1))
                }
            } else list.add(Token(TokenType.Divide, line, column, 1))
            char == '%' -> if (index + 1 < str.length && str[index + 1] == '=') {
                list.add(Token(TokenType.RemainderAssign, line, column, 2))
                index+=2; column+=2;
                continue
            } else list.add(Token(TokenType.Remainder, line, column, 1))
            char == '&' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.ConcatAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '&' -> {
                        list.add(Token(TokenType.BoolAnd, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Concat, line, column, 1))
                }
            } else list.add(Token(TokenType.Concat, line, column, 1))
            char == '<' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.LessEqual, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.LessThan, line, column, 1))
                }
            } else list.add(Token(TokenType.LessThan, line, column, 1))
            char == '>' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.GreaterEqual, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.GreaterThan, line, column, 1))
                }
            } else list.add(Token(TokenType.GreaterThan, line, column, 1))
            char == '=' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.Equal, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Assign, line, column, 1))
                }
            } else list.add(Token(TokenType.Assign, line, column, 1))
            char == '!' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.NotEqual, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.BoolNot, line, column, 1))
                }
            } else list.add(Token(TokenType.BoolNot, line, column, 1))
            char == '|' -> if (index + 1 < str.length && str[index + 1] == '|') {
                list.add(Token(TokenType.BoolOr, line, column, 2))
                index+=2; column+=2
                continue
            } else throw SyntaxError("Unexpected character '|'. Did you mean '||'?", line, column)
            char == '@' -> {
                val xPos = column
                index++; column++
                var s = ""
                while (index < str.length && (str[index].isLetterOrDigit() || str[index] == '_')) {
                    s += str[index]
                    index++; column++
                }
                if (s.isEmpty()) {
                    throw SyntaxError("Annotation starter (@) with no annotation name.", xPos, line)
                }
                list.add(Token(TokenType.Annotation(s), line, xPos, column - xPos))
                continue
            }
            char.isLetter() || char == '_' -> {
                val xPos = column
                var s = ""
                while (index < str.length && (str[index].isLetterOrDigit() || str[index] == '_')) {
                    s += str[index]
                    index++; column++
                }
                when (s) {
                    "let" -> if (index < str.length && str[index] == '!') {
                        index++; column++
                        list.add(Token(TokenType.ImmutableLet, line, xPos, column - xPos))
                    } else {
                        list.add(Token(TokenType.MutableLet, line, xPos, column - xPos))
                    }
                    "fn" -> list.add(Token(TokenType.Function, line, xPos, column - xPos))
                    "proc" -> list.add(Token(TokenType.Process, line, xPos, column - xPos))
                    "if" -> list.add(Token(TokenType.If, line, xPos, column - xPos))
                    "else" -> list.add(Token(TokenType.Else, line, xPos, column - xPos))
                    "for" -> list.add(Token(TokenType.For, line, xPos, column - xPos))
                    "while" -> list.add(Token(TokenType.While, line, xPos, column - xPos))
                    "return" -> list.add(Token(TokenType.Return, line, xPos, column - xPos))
                    "where" -> list.add(Token(TokenType.Where, line, xPos, column - xPos))
                    "struct" -> list.add(Token(TokenType.Struct, line, xPos, column - xPos))
                    "trait" -> list.add(Token(TokenType.Trait, line, xPos, column - xPos))
                    "impl" -> list.add(Token(TokenType.Impl, line, xPos, column - xPos))
                    "private" -> list.add(Token(TokenType.Private, line, xPos, column - xPos))
                    "enum" -> list.add(Token(TokenType.Enum, line, xPos, column - xPos))
                    "import" -> list.add(Token(TokenType.Import, line, xPos, column - xPos))
                    "true" -> list.add(Token(TokenType.True, line, xPos, column - xPos))
                    "false" -> list.add(Token(TokenType.False, line, xPos, column - xPos))
                    else -> list.add(Token(TokenType.Ident(s), line, xPos, column - xPos))
                }
                continue
            }
            char.isDigit() -> {
                val xPos = column
                var num = 0
                while (index < str.length && str[index].isDigit()) {
                    num *= 10
                    num += str[index].digitToInt()
                    index++; column++
                }
                if (str[index] == '.') {
                    index++; column++
                    var float = num.toFloat()
                    var position = 1f
                    while (index < str.length && str[index].isDigit()) {
                        position /= 10
                        float += str[index].digitToInt() * position;
                        index++; column++
                    }
                    list.add(Token(TokenType.Num(float), line, xPos, column - xPos))
                } else if (str[index] == 'f') {
                    list.add(Token(TokenType.Num(num.toFloat()), line, xPos, column - xPos))
                    index++; column++
                } else list.add(Token(TokenType.Int(num), line, xPos, column - xPos))
                continue
            }
            char == '"' || char == '\'' -> {
                val xPos = column
                var s = ""
                var escape = false
                index++; column++
                while (index < str.length) {
                    val c = str[index]
                    if (escape) {
                        s += when (c) {
                            '\\' -> '\\'
                            '$' -> "\\$"
                            'n' -> '\n'
                            'r' -> '\r'
                            't' -> '\t'
                            'b' -> '\b'
                            else -> throw SyntaxError("Invalid escape sequence \\$c", line, column - 1)
                        }
                        escape = false
                    } else {
                        when (c) {
                            '\\' -> escape = true
                            char -> break
                            else -> s += c
                        }
                    }
                    index++; column++
                }
                list.add(Token(TokenType.String(s), line, xPos, column - xPos))
                index++; column++
                continue
            }
            char == '\n' -> {
                column = 0
                line++
            }
            !char.isWhitespace() -> {
                throw SyntaxError("Unexpected character '$char'", line, column)
            }
        }
        index++; column++
    }
    return Tokens(list)
}