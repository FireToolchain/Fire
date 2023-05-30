package dev.ashli.fire.parser.complete.expression.value

import dev.ashli.fire.parser.complete.expression.Expression

open class FireValue : Expression() {
    override fun isPure() = true
}