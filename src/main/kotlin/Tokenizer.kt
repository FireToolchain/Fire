data class Tokens(val list: List<Token>)
data class Token(val type: TokenType, val line: Int, val column: Int, val width: Int)
sealed interface TokenType {
    // Grouping symbols
    object OpenParen : TokenType
    object CloseParen : TokenType
    object OpenBracket : TokenType
    object CloseBracket : TokenType
    object OpenBrace : TokenType
    object CloseBrace : TokenType

    // Operators
    object LessThan : TokenType
    object GreaterThan : TokenType
    object GreaterEqual : TokenType
    object LessEqual : TokenType
    object NotEqual : TokenType
    object Equal : TokenType
    object Plus : TokenType
    object Minus : TokenType
    object Multiply : TokenType
    object Divide : TokenType
    object Remainder : TokenType
    object Concat : TokenType
    object BoolOr : TokenType
    object BoolAnd : TokenType
    object BoolNot : TokenType
    object Arrow : TokenType


    // Structure
    object Assign : TokenType
    object PlusAssign : TokenType
    object MinusAssign : TokenType
    object MultiplyAssign : TokenType
    object DivideAssign : TokenType
    object RemainderAssign : TokenType
    object ConcatAssign : TokenType
    object Colon : TokenType
    object Comma : TokenType
    object Dot : TokenType
    object Semicolon : TokenType
    class Annotation(val str: kotlin.String) : TokenType

    // Keywords
    object MutableLet : TokenType
    object ImmutableLet : TokenType
    object Function : TokenType
    object Process : TokenType
    object If : TokenType
    object Else : TokenType
    object Return : TokenType
    object Struct : TokenType
    object Private : TokenType
    object Impl : TokenType
    object For : TokenType
    object While : TokenType
    object Enum : TokenType
    object Trait : TokenType
    object Import : TokenType
    object Where : TokenType


    // Values
    class Ident(val str: kotlin.String) : TokenType
    class Int(val num: kotlin.Int) : TokenType
    class Num(val num: Float) : TokenType
    class String(val str: kotlin.String) : TokenType
    object True : TokenType
    object False : TokenType

}
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
                        TODO("Line comment")
                    }
                    '*' -> {
                        TODO("Block comment")
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
            char.isLetter() || char == '_' -> {
                val xPos = column
                var s = ""
                while (index < str.length && (str[index].isLetterOrDigit() || str[index] == '_')) {
                    s += str[index]
                    index++; column++
                }
                list.add(Token(TokenType.Ident(s), line, xPos, column - xPos))
                continue
            }
            char.isDigit() -> {
                val xPos = column
                var num = 0
                while (index < str.length && str[index].isDigit()) {
                    num *= 10
                    num += str[index].digitToInt();
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
            // Strings
        }
        index++; column++
    }
    return Tokens(list)
}