package tokenizing

/**
 * Represents a list of tokens.
 */
data class Tokens(val list: List<Token>)

/**
 * Represents a token. Holds its type (with associated data) and its position and width in source code.
 */
data class Token(val type: TokenType, val line: Int, val column: Int, val width: Int)

/**
 * Interface representing tokens that may appear in source code.
 */
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
    object Increment : TokenType
    object Decrement : TokenType
    object Colon : TokenType
    object Comma : TokenType
    object Dot : TokenType
    object Semicolon : TokenType
    data class Annotation(val str: kotlin.String) : TokenType

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
    data class Ident(val str: kotlin.String) : TokenType
    data class Int(val num: kotlin.Int) : TokenType
    data class Num(val num: Float) : TokenType
    data class String(val str: kotlin.String) : TokenType
    object True : TokenType
    object False : TokenType
}