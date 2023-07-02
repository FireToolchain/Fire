package dev.ashli.fire.parser.emitter

sealed class KindlingValue {
    class Text(val value: String) : KindlingValue()
    class EscapedText(val value: String) : KindlingValue()
    class Number(val value: Double) : KindlingValue()
    class List(var value: Array<KindlingValue>) : KindlingValue() {}
    class Identifier(val value: String) : KindlingValue()
}

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