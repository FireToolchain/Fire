package dev.ashli.fire.parser.expression.calculations

import dev.ashli.fire.parser.expression.Expression

/**
 * Represents a Dynamic method call.
 * A method call is dynamic when the actual function to be run is dependent at run-time.
 *
 * For example:
 * a.method(params...), where a's type is a Trait.
 *
 * Math operations may be dynamic: a + b becomes A::add(a, b), which may become a.add(b) if A's type is unknown.
 */
class DynamicMethodCall : Expression() {
    override fun isPure() = false // Maybe
}