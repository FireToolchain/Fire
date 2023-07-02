package dev.ashli.fire.parser.emitter

import kotlin.math.pow


sealed class KindlingArgument {
    class Text(val value: String) : KindlingArgument()
    class Number(val value: Float) : KindlingArgument()
    class Location(val x: Float, val y: Float, val z: Float, val pitch: Float, val yaw: Float) : KindlingArgument()
    class Vector(val x: Float, val y: Float, val z: Float) : KindlingArgument()
    class Potion(val type: String, val ticks: Int, val amplifier: Int) : KindlingArgument()
    class Sound(val type: String, val variant: String?, val pitch: Float, val volume: Float) : KindlingArgument()
    class GameValue(val type: String, val selector: String) : KindlingArgument()
    class BlockTag(val type: String, val value: String, val variable: KindlingArgument?) : KindlingArgument()
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
        is KindlingArgument.Potion -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("pot"),
                KindlingValue.Identifier(value.type),
                KindlingValue.Number(value.ticks.toDouble()),
                KindlingValue.Number(value.amplifier.toDouble()),
            ))
        }
        is KindlingArgument.Sound -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("sound"),
                KindlingValue.Identifier(value.type),
                KindlingValue.Number(value.pitch.toDouble()),
                KindlingValue.Number(value.volume.toDouble()),
            ))
        }
        is KindlingArgument.BlockTag -> {
            value.variable?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("tag"),
                    KindlingValue.Identifier(value.type),
                    KindlingValue.Text(value.value),
                    emitArgument(value.variable),
                ))
            }
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("tag"),
                KindlingValue.Identifier(value.type),
                KindlingValue.Text(value.value),
            ))
        }
        is KindlingArgument.GameValue -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("val"),
                KindlingValue.Text(value.type),
                KindlingValue.Identifier(value.selector),
            ))
        }
        is KindlingArgument.Item -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("item"),
                KindlingValue.EscapedText(value.nbt),
            ))
        }
        is KindlingArgument.ScopedVariable -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("var"),
                KindlingValue.Text(value.name),
            ))
        }
        is KindlingArgument.LocalVariable -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("local"),
                KindlingValue.Text(value.name),
            ))
        }is KindlingArgument.GlobalVariable -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("global"),
                KindlingValue.Text(value.name),
            ))
        }
        is KindlingArgument.SaveVariable -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("save"),
                KindlingValue.Text(value.name),
            ))
        }
        is KindlingArgument.GetParam -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("param"),
                KindlingValue.Number(value.index.toDouble()),
            ))
        }
        is KindlingArgument.ReturnedValue -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("ret"),
            ))
        }
        is KindlingArgument.Particle -> {
            var list = KindlingValue.List(arrayOf(
                KindlingValue.Text(value.type)
            ))
            list.value += KindlingValue.List(arrayOf(
                KindlingValue.Identifier("amount"),
                KindlingValue.Number(value.settings.amount.toDouble())
            ))
            if(value.settings.material != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("mat"),
                    KindlingValue.Text(value.settings.material)
                ))
            }
            if(value.settings.motionX != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("motion-x"),
                    KindlingValue.Number(value.settings.motionX.toDouble())
                ))
            }
            if(value.settings.motionY != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("motion-y"),
                    KindlingValue.Number(value.settings.motionY.toDouble())
                ))
            }
            if(value.settings.motionZ != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("motion-z"),
                    KindlingValue.Number(value.settings.motionZ.toDouble())
                ))
            }

            if(value.settings.spreadX != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("spread-x"),
                    KindlingValue.Number(value.settings.spreadX.toDouble())
                ))
            }
            if(value.settings.spreadY != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("spread-y"),
                    KindlingValue.Number(value.settings.spreadY.toDouble())
                ))
            }

            if(value.settings.roll != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("roll"),
                    KindlingValue.Number(value.settings.roll.toDouble())
                ))
            }
            if(value.settings.size != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("size"),
                    KindlingValue.Number(value.settings.size.toDouble())
                ))
            }
            if(value.settings.rgb != null) {
                val i = 256.0.pow(2.0) * value.settings.rgb.r + 256 * value.settings.rgb.g + value.settings.rgb.b;

                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("color"),
                    KindlingValue.Number(
                        i.toDouble()
                    )
                ))
            }
            if(value.settings.material != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("material"),
                    KindlingValue.Text(value.settings.material)
                ))
            }
            if(value.settings.variationColor != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("variation-color"),
                    KindlingValue.Number(value.settings.variationColor.toDouble())
                ))
            }
            if(value.settings.variationSize != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("variation-size"),
                    KindlingValue.Number(value.settings.variationSize.toDouble())
                ))
            }
            if(value.settings.variationMotion != null) {
                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("variation-motion"),
                    KindlingValue.Number(value.settings.variationMotion.toDouble())
                ))
            }

            return list
        }

    }
}