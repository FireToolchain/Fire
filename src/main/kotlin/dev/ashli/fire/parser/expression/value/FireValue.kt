package dev.ashli.fire.parser.expression.value

import dev.ashli.fire.parser.expression.Expression

open class FireValue : Expression() {
    override fun isPure() = true
}