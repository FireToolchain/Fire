package dev.ashli.fire.parser.emitter

sealed class KindlingValue {
    class Text(val value: String) : KindlingValue()
    class EscapedText(val value: String) : KindlingValue()
    class Number(val value: Double) : KindlingValue()
    class List(val value: Array<KindlingValue>) : KindlingValue() {}
    class Identifier(val value: String) : KindlingValue()



    companion object {
        fun emitValue(value: KindlingValue): String {
            return when(value) {
                is Number -> "${value.value}"
                is Text -> "\"${value.value}\""
                is EscapedText -> "'${value.value}'"
                is List -> {
                    var builder = "("
                    for(item in value.value) {
                        builder = "$builder ${emitValue(item)}"
                    }
                    builder = "$builder )"
                    builder
                }
                is Identifier -> value.value
            }
        }
    }
}

sealed class KindlingHeader {
    class PlayerEvent(val name: String, val code: Array<KindlingAction>) {}
    class EntityEvent(val name: String, val code: Array<KindlingAction>) {}
    class Function(val name: String, val code: Array<KindlingAction>) {}
    class Process(val name: String, val code: Array<KindlingAction>) {}
}

sealed class KindlingAction {
    class PlayerAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>)
    class EntityAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>)
}

sealed class KindlingArgument {
    class Text(val value: String)
    class Number(val value: Float)
    class Location(val x: Float, val y: Float, val z: Float, val pitch: Float, val yaw: Float)
    class Vector(val x: Float, val y: Float, val z: Float)
    class Potion(val type: String, val ticks: Int, val amplifier: Int)
    class BlockTag(val type: String, val value: String, val variable: String?)
    class Item(val nbt: String)
    class Particle(val type: String, val settings: String) // TODO: make seperate type for settings
}
