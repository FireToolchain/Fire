package dev.ashli.fire.parser.expression.calculations

import dev.ashli.fire.parser.expression.Expression

/**
 * Represents an Assignment expression.
 */
class Assignment : Expression() {
    override fun isPure() = false
}