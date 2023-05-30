package dev.ashli.fire.parser.expression.calculations

import dev.ashli.fire.parser.expression.Expression

/**
 * Represents a static function call.
 * For example:
 * function(params...)
 */
class StaticFunctionCall : Expression() {
    override fun isPure() = false // Maybe
}