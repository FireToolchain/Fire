package dev.ashli.fire.parser.complete.expression.calculations

import dev.ashli.fire.parser.complete.expression.Expression

/**
 * Represents a static function call.
 * For example:
 * function(params...)
 */
class StaticFunctionCall : Expression() {
    override fun isPure() = false // Maybe
}