package dev.ashli.fire.parser.emitter

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