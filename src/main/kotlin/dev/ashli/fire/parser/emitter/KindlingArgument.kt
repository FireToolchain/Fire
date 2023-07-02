package dev.ashli.fire.parser.emitter

import kotlin.math.pow

/**
 * Each item in this class represents an argument to an action in Kindling, or an argument to an action in DF.
 * @see KindlingAction
 */
sealed class KindlingArgument {
    /**
     * This represents the Text value.
     * @param value The value of the text.
     */
    class Text(val value: String) : KindlingArgument()
    /**
     * This represents the Number value.
     * @param value The value of the number.
     */
    class Number(val value: Float) : KindlingArgument()
    /**
     * This represents the Location value.
     * @param x X coordinate.
     * @param y X coordinate.
     * @param z X coordinate.
     * @param pitch Pitch coordinate.
     * @param yaw Yaw coordinate.
     */
    class Location(val x: Float, val y: Float, val z: Float, val pitch: Float, val yaw: Float) : KindlingArgument()
    /**
     * This represents the Vector value.
     * @param x X coordinate.
     * @param y X coordinate.
     * @param z X coordinate.
     */
    class Vector(val x: Float, val y: Float, val z: Float) : KindlingArgument()
    /**
     * This represents the Potion value.
     * @param type The effect that the potion gives.
     * @param ticks How long the potion lasts in ticks.
     * @param amplifier The amplifier of the potion.
     */
    class Potion(val type: String, val ticks: Int, val amplifier: Int) : KindlingArgument()
    /**
     * This represents the Sound value.
     * @param type The sound to play.
     * @param variant If it is a sound with multiple sub-sounds, it's which sound to play.
     * @param pitch The pitch of the sound.
     * @param volume The volume of the sound.
     */
    class Sound(val type: String, val variant: String?, val pitch: Float, val volume: Float) : KindlingArgument()
    /**
     * This represents the Game Value.
     * @param type The game value to track.
     * @param selector Who are we tracking with this? Only applies to certain values
     */
    class GameValue(val type: String, val selector: String) : KindlingArgument()
    /**
     * This represents a Block Tag argument.
     * @param type The block tag to track.
     * @param value Represents the option of the block tag.
     * @param variable If a block tag accepts a variable - put it here.
     * @see KindlingArgument
     */
    class BlockTag(val type: String, val value: String, val variable: KindlingArgument?) : KindlingArgument()
    /**
     * This represents an Item.
     * @param nbt The NBT to use for the item.
     */
    class Item(val nbt: String) : KindlingArgument()
    /**
     * This represents a Particle value.
     * @param type The type of particle to use.
     * @param settings The settings of the particle.
     */
    class Particle(val type: String, val settings: ParticleSettings) : KindlingArgument()
    /**
     * A variable local to the function.
     * @param name Name of the variable.
     */
    class ScopedVariable(val name: String) : KindlingArgument()
    /**
     * A variable local to the thread.
     * @param name Name of the variable.
     */
    class LocalVariable(val name: String) : KindlingArgument()
    /**
     * A variable local to the current game running.
     * @param name Name of the variable.
     */
    class GlobalVariable(val name: String) : KindlingArgument()
    /**
     * A variable that persists throughout time.
     * @param name Name of the variable.
     */
    class SaveVariable(val name: String) : KindlingArgument()
    /**
     * The result of a function parameter.
     * @param index The index to get.
     */
    class GetParam(val index: Int) : KindlingArgument()

    /**
     * The last returned result of a function.
     */
    class ReturnedValue : KindlingArgument()
}

/**
 * This function is responsible for transforming KindlingArguments into KindlingValues.
 * @param value The value to transform.
 * @return The Kindling representation of the argument.
 * @see KindlingValue
 */
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
            value.variant?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("sound"),
                    KindlingValue.Text(value.type),
                    KindlingValue.Text(value.variant),
                    KindlingValue.Number(value.pitch.toDouble()),
                    KindlingValue.Number(value.volume.toDouble()),
                ))
            }
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
            val list = KindlingValue.List(arrayOf(
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
                val i = 256.0.pow(2.0) * value.settings.rgb.r + 256 * value.settings.rgb.g + value.settings.rgb.b

                list.value += KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("color"),
                    KindlingValue.Number(i)
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