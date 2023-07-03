package dev.ashli.fire.parser.emitter

/**
 * Kindling is the output of Fire. However, we need a way to represent Kindling in its rawest form as safely as possible.
 * This is what KindlingValue is for. This allows us to ensure that the output will not cause any erroneous errors in the lexer.
 * It also prevents issues like incorrect parenthesis "(()))", or invalid types.
 */
sealed class KindlingValue {
    /**
     * This represents a string value.
     * e.x "Hello world!"
     * @param value The value of the string.
     */
    class Text(val value: String) : KindlingValue()
    /**
     * This represents an escaped string value.
     * e.x 'Hello world!'
     * @param value The value of the escaped string.
     */
    class EscapedText(val value: String) : KindlingValue()
    /**
     * This represents a number value.
     * e.x 12.76
     * @param value The value of the number.
     */
    class Number(val value: Double) : KindlingValue()
    /**
     * This represents a list of multiple KindlingValues.
     * e.x (12.6 "good night" 12 hi)
     * @param value An array of KindlingValues to output.
     */
    class List(var value: Array<KindlingValue>) : KindlingValue()
    /**
     * This represents an identifier.
     * e.x hi
     * @param value The identifier to output.
     */
    class Identifier(val value: String) : KindlingValue()
}

/**
 * This function is responsible for emitting the values provided into a string.
 * @param value This is the value you wish to transform.
 * @return The output of the KindlingValue. This should be valid to the lexer.
 */
fun emitValue(value: KindlingValue): String {
    return when(value) {
        is KindlingValue.Number -> "${value.value}"
        is KindlingValue.Text -> "\"${value.value}\""
        is KindlingValue.EscapedText -> "'${value.value}'"
        is KindlingValue.List -> {
            var builder = "("
            for(item in value.value) {
                builder = "$builder ${emitValue(item)}"
            }
            builder = "$builder )"
            builder
        }
        is KindlingValue.Identifier -> value.value
    }
}