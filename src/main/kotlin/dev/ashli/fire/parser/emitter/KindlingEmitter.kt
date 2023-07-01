package dev.ashli.fire.parser.emitter

sealed class KindlingValue {
    data class Text(val value: String) : KindlingValue()
    data class EscapedText(val value: String) : KindlingValue()
    data class Number(val value: Double) : KindlingValue()
    data class List(val value: Array<KindlingValue>) : KindlingValue() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as List

            return value.contentEquals(other.value)
        }

        override fun hashCode(): Int {
            return value.contentHashCode()
        }
    }
    data class Identifier(val value: String) : KindlingValue()
}

fun emit(value: KindlingValue): String {
    return when(value) {
        is KindlingValue.Number -> "${value.value}"
        is KindlingValue.Text -> "\"${value.value}\""
        is KindlingValue.EscapedText -> "'${value.value}'"
        is KindlingValue.List -> {
            var builder = "("
            for(item in value.value) {
                builder = "$builder ${emit(item)}"
            }
            builder = "$builder )"
            builder
        }
        is KindlingValue.Identifier -> value.value
    }
}