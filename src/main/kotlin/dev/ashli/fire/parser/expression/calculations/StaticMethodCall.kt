package dev.ashli.fire.parser.expression.calculations

import dev.ashli.fire.parser.expression.Expression

/**
 * Represents a static method call.
 * For example:
 * a.method(params...), where a's type is a Struct/Enum (not a Trait)
 */
class StaticMethodCall : Expression() {
    override fun isPure() = false // Maybe
}