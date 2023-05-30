package dev.ashli.fire.parser.complete.type

sealed class Type {
    /**
     * Checks if this type conforms to another.
     *
     * A normal type should conform to another if it is equal to, or a subtype of, the other type.
     * A normal type should conform to a generic if it is equal to or a subtype of all constraints.
     * A generic should conform to a normal type if one of its constraints is equal to or a subtype of the other.
     * A generic should conform to another if every constraint has a matching constraint that it is equal to or a subtype of.
     *
     * In short: normal type conforming rules.
     */
    abstract fun conformsTo(other: Type): Boolean
}