package dev.ashli.fire.parser.emitter

sealed class KindlingValue {
    class Text(val value: String) : KindlingValue()
    class EscapedText(val value: String) : KindlingValue()
    class Number(val value: Double) : KindlingValue()
    class List(val value: Array<KindlingValue>) : KindlingValue() {}
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
sealed class KindlingHeader {
    class PlayerEvent(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    class EntityEvent(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    class Function(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    class Process(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
}

fun emitHeader(self: KindlingHeader): KindlingValue {
    return when(self) {
        is KindlingHeader.PlayerEvent -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("player-event"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
        is KindlingHeader.EntityEvent -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("entity-event"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
        is KindlingHeader.Function -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("def"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
        is KindlingHeader.Process -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("process"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
    }

}

sealed class KindlingAction {
    class PlayerAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    class EntityAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
}

fun emitAction(self: KindlingAction): KindlingValue {
    return when(self) {
        is KindlingAction.PlayerAction -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("player-action"),
                KindlingValue.Identifier(self.type),
                KindlingValue.Identifier(self.selector),
            ))
        }
        is KindlingAction.EntityAction -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("entity-action"),
                KindlingValue.Identifier(self.type),
                KindlingValue.Identifier(self.selector),
            ))
        }
    }
}

fun emitActions(self: Array<KindlingAction>): Array<KindlingValue> {
    var out: Array<KindlingValue> = arrayOf()
    for(i in self) {
        out += emitAction(i)
    }
    return out
}

sealed class KindlingArgument {
    class Text(val value: String) : KindlingArgument()
    class Number(val value: Float) : KindlingArgument()
    class Location(val x: Float, val y: Float, val z: Float, val pitch: Float, val yaw: Float) : KindlingArgument()
    class Vector(val x: Float, val y: Float, val z: Float) : KindlingArgument()
    class Potion(val type: String, val ticks: Int, val amplifier: Int) : KindlingArgument()
    class BlockTag(val type: String, val value: String, val variable: String?) : KindlingArgument()
    class Item(val nbt: String) : KindlingArgument()
    class Particle(val type: String, val settings: ParticleSettings) : KindlingArgument() // TODO: make seperate type for settings
    class ScopedVariable(val name: String) : KindlingArgument()
    class LocalVariable(val name: String) : KindlingArgument()
    class GlobalVariable(val name: String) : KindlingArgument()
    class SaveVariable(val name: String) : KindlingArgument()
    class GetParam(val index: Int) : KindlingArgument()
    class ReturnedValue : KindlingArgument()
}

fun emitArgument(value: KindlingArgument): KindlingValue {
    return when(value) {
        is KindlingArgument.Text -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("text"),
                KindlingValue.Text(value.value)
            ))
        }
        is KindlingArgument.Number -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("number"),
                KindlingValue.Number(value.value.toDouble())
            ))
        }
        is KindlingArgument.Location -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("loc"),
                KindlingValue.Number(value.x.toDouble()),
                KindlingValue.Number(value.y.toDouble()),
                KindlingValue.Number(value.z.toDouble()),
                KindlingValue.Number(value.pitch.toDouble()),
                KindlingValue.Number(value.yaw.toDouble()),
            ))
        }
        is KindlingArgument.Vector -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("vec"),
                KindlingValue.Number(value.x.toDouble()),
                KindlingValue.Number(value.y.toDouble()),
                KindlingValue.Number(value.z.toDouble()),
            ))
        }
        else -> TODO()
    }
}

class ParticleSettings {
    val amount: Int = 0
    val spreadX: Int? = null
    val spreadY: Int? = null
    val motionX: Int? = null
    val motionY: Int? = null
    val motionZ: Int? = null
    val roll: Int? = null
    val size: Int? = null
    val rgb: ParticleRGB? = null
    val material: String? = null // should be a valid material
    val variationColor: Int? = null // between 1-100
    val variationMotion: Int? = null // between 1-100
    val variationSize: Int? = null // between 1-100
}

class ParticleRGB {
    val r: Int = 0
    val g: Int = 0
    val b: Int = 0
}
