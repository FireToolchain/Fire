package dev.ashli.fire.parser.complete.expression

/**
 * Represents a general Fire expression such as "function(params...)" or "2".
 */
abstract class Expression {
    /**
     * Determines if an expression has side effects for evaluating it.
     * If a function is pure, it has no side effects, and may be optimized away.
     *
     * Example: 2 + 2 has no side effects, so a single statement: 2 + 2; may be removed from a program
     * because it does nothing.
     *
     * @return True if the expression is pure.
     */
    abstract fun isPure(): Boolean
}