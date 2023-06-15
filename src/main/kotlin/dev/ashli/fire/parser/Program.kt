package dev.ashli.fire.parser

import dev.ashli.fire.resources.ResourceMap
import dev.ashli.fire.resources.ResourceName
import dev.ashli.fire.tokenizer.tokenize
import java.io.File

/**
 * Represents a Fire program. Stores a map to generic resources.
 */
class Program(files: Array<File>) {
    private val resourceMap = ResourceMap<TopLevelResource>()
    init {
        for (f in files) {
            val tokens = tokenize(f.readText(), ResourceName(f.nameWithoutExtension))
            // TODO parse the tokens and add found resources into the resourceMap
        }
    }

}