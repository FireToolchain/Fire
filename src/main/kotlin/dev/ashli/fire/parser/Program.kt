package dev.ashli.fire.parser

import dev.ashli.fire.CLIArg
import dev.ashli.fire.CommandLineArguments
import dev.ashli.fire.resources.ResourceMap
import dev.ashli.fire.resources.ResourceName
import dev.ashli.fire.tokenizer.tokenize
import java.io.File

/**
 * Represents a Fire program. Stores a map to generic resources.
 */
class Program(files: Array<File>, arguments: CommandLineArguments) {
    private val resourceMap = ResourceMap<TopLevelResource>()
    init {
        for (f in files) {
            val tokens = tokenize(f.readText(), ResourceName(f.nameWithoutExtension))

            // If emitting tokens is enabled, write the tokens to `stdout`.
            if(arguments.hasArgument(CLIArg.Emit("tokens"))) {
                println("Printing token from: $f (Enable with flag `--emit tokens`, remove this argument to disable.)")
                println(tokens.list.joinToString("\n") {
                    it.type.toString()
                })
                println("\n")
            }
            // TODO parse the tokens and add found resources into the resourceMap
        }
    }

}