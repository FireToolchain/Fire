package dev.ashli.fire.tokenizer

import dev.ashli.fire.resources.ResourceName

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
    data object StaticFunction : TokenType
    data object Process : TokenType
    data object If : TokenType
    data object Else : TokenType
    data object Return : TokenType
    data object Break : TokenType
    data object Continue : TokenType
    data object Struct : TokenType
    data object Private : TokenType
    data object Impl : TokenType
    data object For : TokenType
    data object While : TokenType
    data object Enum : TokenType
    data object Trait : TokenType
    data object Import : TokenType
    data object Where : TokenType
    data object This : TokenType
    data object ThisType : TokenType


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
fun tokenize(str: String, fileName: ResourceName): Tokens {
    val list = mutableListOf<Token>()
    var line = 1
    var column = 0
    var index = 0
    while (index < str.length) {
        val char = str[index]
        when {
            char == '(' -> list.add(Token(TokenType.OpenParen, SingleTokenPosition(line, column, fileName)))
            char == ')' -> list.add(Token(TokenType.CloseParen, SingleTokenPosition(line, column, fileName)))
            char == '[' -> list.add(Token(TokenType.OpenBracket, SingleTokenPosition(line, column, fileName)))
            char == ']' -> list.add(Token(TokenType.CloseBracket, SingleTokenPosition(line, column, fileName)))
            char == '{' -> list.add(Token(TokenType.OpenBrace, SingleTokenPosition(line, column, fileName)))
            char == '}' -> list.add(Token(TokenType.CloseBrace, SingleTokenPosition(line, column, fileName)))
            char == ',' -> list.add(Token(TokenType.Comma, SingleTokenPosition(line, column, fileName)))
            char == '.' -> list.add(Token(TokenType.Dot, SingleTokenPosition(line, column, fileName)))
            char == ';' -> list.add(Token(TokenType.Semicolon, SingleTokenPosition(line, column, fileName)))
            char == ':' -> list.add(Token(TokenType.Colon, SingleTokenPosition(line, column, fileName)))
            char == '+' -> if (index + 1 < str.length && str[index + 1] == '=') {
                list.add(Token(TokenType.PlusAssign, WholeTokenPosition(line, column, 2, fileName)))
                index+=2; column+=2
                continue
            } else list.add(Token(TokenType.Plus, SingleTokenPosition(line, column, fileName)))
            char == '-' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.MinusAssign, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    '>' -> {
                        list.add(Token(TokenType.Arrow, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Minus, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.Minus, SingleTokenPosition(line, column, fileName)))
            char == '*' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.MultiplyAssign, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Multiply, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.Multiply, SingleTokenPosition(line, column, fileName)))
            char == '/' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.DivideAssign, SingleTokenPosition(line, column, fileName)))
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
                    else -> list.add(Token(TokenType.Divide, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.Divide, SingleTokenPosition(line, column, fileName)))
            char == '%' -> if (index + 1 < str.length && str[index + 1] == '=') {
                list.add(Token(TokenType.RemainderAssign, WholeTokenPosition(line, column, 2, fileName)))
                index+=2; column+=2;
                continue
            } else list.add(Token(TokenType.Remainder, SingleTokenPosition(line, column, fileName)))
            char == '&' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.ConcatAssign, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    '&' -> {
                        list.add(Token(TokenType.BoolAnd, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Concat, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.Concat, SingleTokenPosition(line, column, fileName)))
            char == '<' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.LessEqual, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.LessThan, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.LessThan, SingleTokenPosition(line, column, fileName)))
            char == '>' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.GreaterEqual, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.GreaterThan, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.GreaterThan, SingleTokenPosition(line, column, fileName)))
            char == '=' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.Equal, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.Assign, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.Assign, SingleTokenPosition(line, column, fileName)))
            char == '!' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(TokenType.NotEqual, SingleTokenPosition(line, column, fileName)))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(TokenType.BoolNot, SingleTokenPosition(line, column, fileName)))
                }
            } else list.add(Token(TokenType.BoolNot, SingleTokenPosition(line, column, fileName)))
            char == '|' -> if (index + 1 < str.length && str[index + 1] == '|') {
                list.add(Token(TokenType.BoolOr, WholeTokenPosition(line, column, 2, fileName)))
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
                list.add(Token(TokenType.Annotation(s), WholeTokenPosition(line, xPos, column - xPos, fileName)))
                continue
            }
            char.isLetter() || char == '_' -> {
                val xPos = column
                var s = ""
                while (index < str.length && (str[index].isLetterOrDigit() || str[index] == '_' || str[index] == '!')) {
                    s += str[index]
                    index++; column++
                }
                val position = WholeTokenPosition(line, xPos, column - xPos, fileName)
                when (s) {
                    "let!" -> list.add(Token(TokenType.ImmutableLet, position))
                    "let" -> list.add(Token(TokenType.MutableLet, position))
                    "fn" -> list.add(Token(TokenType.Function, position))
                    "fn!" -> list.add(Token(TokenType.StaticFunction, position))
                    "proc" -> list.add(Token(TokenType.Process, position))
                    "if" -> list.add(Token(TokenType.If, position))
                    "else" -> list.add(Token(TokenType.Else, position))
                    "for" -> list.add(Token(TokenType.For, position))
                    "while" -> list.add(Token(TokenType.While, position))
                    "return" -> list.add(Token(TokenType.Return, position))
                    "break" -> list.add(Token(TokenType.Break, position))
                    "continue" -> list.add(Token(TokenType.Continue, position))
                    "this" -> list.add(Token(TokenType.This, position))
                    "This" -> list.add(Token(TokenType.ThisType, position))
                    "where" -> list.add(Token(TokenType.Where, position))
                    "struct" -> list.add(Token(TokenType.Struct, position))
                    "trait" -> list.add(Token(TokenType.Trait, position))
                    "impl" -> list.add(Token(TokenType.Impl, position))
                    "private" -> list.add(Token(TokenType.Private, position))
                    "enum" -> list.add(Token(TokenType.Enum, position))
                    "import" -> list.add(Token(TokenType.Import, position))
                    "true" -> list.add(Token(TokenType.True, position))
                    "false" -> list.add(Token(TokenType.False, position))
                    else -> list.add(Token(TokenType.Ident(s), position))
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
                    list.add(Token(TokenType.Num(float), WholeTokenPosition(line, xPos, column - xPos, fileName)))
                } else if (str[index] == 'f') {
                    list.add(Token(TokenType.Num(num.toFloat()), WholeTokenPosition(line, xPos, column - xPos, fileName)))
                    index++; column++
                } else list.add(Token(TokenType.Int(num), WholeTokenPosition(line, xPos, column - xPos, fileName)))
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
                list.add(Token(TokenType.String(s), WholeTokenPosition(line, xPos, column - xPos, fileName)))
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
    return Tokens(list.toTypedArray(), fileName)
}