package tokenizing

import tokenizing.TokenType.*

fun tokenize(str: String): Tokens {
    val list = mutableListOf<Token>()
    var line = 1
    var column = 0
    var index = 0
    while (index < str.length) {
        val char = str[index]
        when {
            char == '(' -> list.add(Token(OpenParen, line, column, 1))
            char == ')' -> list.add(Token(CloseParen, line, column, 1))
            char == '[' -> list.add(Token(OpenBracket, line, column, 1))
            char == ']' -> list.add(Token(CloseBracket, line, column, 1))
            char == '{' -> list.add(Token(OpenBrace, line, column, 1))
            char == '}' -> list.add(Token(CloseBrace, line, column, 1))
            char == ',' -> list.add(Token(Comma, line, column, 1))
            char == '.' -> list.add(Token(Dot, line, column, 1))
            char == ';' -> list.add(Token(Semicolon, line, column, 1))
            char == ':' -> list.add(Token(Colon, line, column, 1))
            char == '+' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(PlusAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '+' -> {
                        list.add(Token(Increment, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(Plus, line, column, 1))
                }
            } else list.add(Token(Minus, line, column, 1))
            char == '-' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(MinusAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '-' -> {
                        list.add(Token(Decrement, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '>' -> {
                        list.add(Token(Arrow, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(Minus, line, column, 1))
                }
            } else list.add(Token(Minus, line, column, 1))
            char == '*' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(MultiplyAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(Multiply, line, column, 1))
                }
            } else list.add(Token(Multiply, line, column, 1))
            char == '/' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(DivideAssign, line, column, 1))
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
                    else -> list.add(Token(Divide, line, column, 1))
                }
            } else list.add(Token(Divide, line, column, 1))
            char == '%' -> if (index + 1 < str.length && str[index + 1] == '=') {
                list.add(Token(RemainderAssign, line, column, 2))
                index+=2; column+=2
                continue
            } else list.add(Token(Remainder, line, column, 1))
            char == '&' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(ConcatAssign, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    '&' -> {
                        list.add(Token(BoolAnd, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(Concat, line, column, 1))
                }
            } else list.add(Token(Concat, line, column, 1))
            char == '<' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(LessEqual, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(LessThan, line, column, 1))
                }
            } else list.add(Token(LessThan, line, column, 1))
            char == '>' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(GreaterEqual, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(GreaterThan, line, column, 1))
                }
            } else list.add(Token(GreaterThan, line, column, 1))
            char == '=' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(Equal, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(Assign, line, column, 1))
                }
            } else list.add(Token(Assign, line, column, 1))
            char == '!' -> if (index + 1 < str.length) {
                when (str[index + 1]) {
                    '=' -> {
                        list.add(Token(NotEqual, line, column, 1))
                        index+=2; column+=2
                        continue
                    }
                    else -> list.add(Token(BoolNot, line, column, 1))
                }
            } else list.add(Token(BoolNot, line, column, 1))
            char == '|' -> if (index + 1 < str.length && str[index + 1] == '|') {
                list.add(Token(BoolOr, line, column, 2))
                index+=2; column+=2
                continue
            } else throw SyntaxError("Unexpected character '|'. Did you mean '||'?", line, column)
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
                        list.add(Token(ImmutableLet, line, xPos, column - xPos))
                    } else {
                        list.add(Token(MutableLet, line, xPos, column - xPos))
                    }
                    "fn" -> list.add(Token(Function, line, xPos, column - xPos))
                    "proc" -> list.add(Token(Process, line, xPos, column - xPos))
                    "if" -> list.add(Token(If, line, xPos, column - xPos))
                    "else" -> list.add(Token(Else, line, xPos, column - xPos))
                    "for" -> list.add(Token(For, line, xPos, column - xPos))
                    "while" -> list.add(Token(While, line, xPos, column - xPos))
                    "return" -> list.add(Token(Return, line, xPos, column - xPos))
                    "where" -> list.add(Token(Where, line, xPos, column - xPos))
                    "struct" -> list.add(Token(Struct, line, xPos, column - xPos))
                    "trait" -> list.add(Token(Trait, line, xPos, column - xPos))
                    "impl" -> list.add(Token(Impl, line, xPos, column - xPos))
                    "private" -> list.add(Token(Private, line, xPos, column - xPos))
                    "enum" -> list.add(Token(TokenType.Enum, line, xPos, column - xPos))
                    "import" -> list.add(Token(Import, line, xPos, column - xPos))
                    "true" -> list.add(Token(True, line, xPos, column - xPos))
                    "false" -> list.add(Token(False, line, xPos, column - xPos))
                    else -> list.add(Token(Ident(s), line, xPos, column - xPos))
                }
                continue
            }
            char == '@' -> {
                val xPos = column
                index++; column++
                var s = ""
                while (index < str.length && (str[index].isLetterOrDigit() || str[index] == '_')) {
                    s += str[index]
                    index++; column++
                }
                list.add(Token(Annotation(s), line, xPos, column - xPos))
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
                        float += str[index].digitToInt() * position
                        index++; column++
                    }
                    list.add(Token(Num(float), line, xPos, column - xPos))
                } else if (str[index] == 'f') {
                    list.add(Token(Num(num.toFloat()), line, xPos, column - xPos))
                    index++; column++
                } else list.add(Token(Int(num), line, xPos, column - xPos))
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
                list.add(Token(String(s), line, xPos, column - xPos))
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