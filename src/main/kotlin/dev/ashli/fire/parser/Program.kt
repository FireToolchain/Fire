package dev.ashli.fire.parser

import dev.ashli.fire.resources.ResourceMap
import dev.ashli.fire.tokenizer.Tokens

/**
 * Represents a Fire program. Stores a map to generic resources.
 */
class Program {

    val resourceMap = ResourceMap<Resource>()
}