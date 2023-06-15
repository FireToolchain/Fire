package dev.ashli.fire.tokenizer

import dev.ashli.fire.resources.ResourceName

/**
 * Represents a single token. Includes its type and position data.
 */
data class Token(val type: TokenType, val position: TokenPosition)

/**
 * Represents a token's position
 */
abstract class TokenPosition(val line: Int, val column: Int, val fileName: ResourceName) {
    /**
     * The column directly after this position
     */
    abstract fun finalColumn(): Int
}

/**
 * Represents the position in a code file.
 */
class SingleTokenPosition(line: Int, column: Int, fileName: ResourceName) : TokenPosition(line, column, fileName) {
    override fun toString() = "in $fileName, at: line $line, column $column"
    override fun finalColumn() = column + 1
}

/**
 * Represents the position of a token in a file, including its width.
 */
class WholeTokenPosition(line: Int, column: Int, val width: Int, fileName: ResourceName) : TokenPosition(line, column, fileName) {
    override fun toString() = "in $fileName, at: line $line, column $column"
    override fun finalColumn() = column + width
}