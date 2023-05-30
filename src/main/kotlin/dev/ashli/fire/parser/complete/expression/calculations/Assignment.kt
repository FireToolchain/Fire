package dev.ashli.fire.parser.complete.expression.calculations

import dev.ashli.fire.parser.complete.expression.Expression

/**
 * Represents an Assignment expression.
 */
class Assignment : Expression() {
    override fun isPure() = false
}