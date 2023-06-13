package dev.ashli.fire.compilation.settings

import dev.ashli.fire.compilation.settings.output.CommandOutput
import dev.ashli.fire.compilation.settings.output.OutputType
import dev.ashli.fire.diamondfire.Rank

/**
 * Represents settings for Compilation of a Fire program.
 */
class CompilationSettings(
    val maxSize: Int = 50, // Max Plot Size
    val bundle: Boolean = true, // Have kindling bundle templates together
    val outputType: OutputType = CommandOutput(), // The output handler to use (Commands, Recode, etc)
    val rank: Rank = Rank.NONE, // The rank at which to compile
)