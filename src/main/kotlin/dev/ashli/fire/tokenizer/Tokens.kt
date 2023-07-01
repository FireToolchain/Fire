package dev.ashli.fire.tokenizer

import dev.ashli.fire.resources.ResourceName

/**
 * Represents a list of tokens.
 */
class Tokens(val list: Array<Token>, private val fileName: ResourceName) : Iterator<Token> {
    private var index = 0
    private var lastLine = 0
    private var lastColumn = 0

    /**
     * @return The number of tokens left in the iterator.
     */
    fun size() = list.size - index

    /**
     * @return The next token in the iterator.
     * @throws IndexOutOfBoundsException If hasNext() is false.
     */
    override fun next(): Token {
        val out = list.getOrNull(index) ?: throw IndexOutOfBoundsException("No more tokens left.")
        lastLine = out.position.line
        lastColumn = out.position.finalColumn()
        index++
        return out
    }

    /**
     * @return True if there's more tokens in the iterator.
     */
    override fun hasNext() = index < list.size

    /**
     * @return The token at the specified input index of the iterator.
     * @throws IndexOutOfBoundsException If index >= size()
     */
    fun peek(i: Int = 0) = list.getOrNull(index + i) ?: throw IndexOutOfBoundsException("Token index out of bounds")

    /**
     * @return The current position of the token iterator
     */
    fun position() = SingleTokenPosition(lastLine, lastColumn, fileName)
}