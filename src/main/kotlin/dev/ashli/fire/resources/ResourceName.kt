package dev.ashli.fire.resources

/**
 * Represents a valid Fire resource name.
 * Fire Resources must match the RegEx /^[A-Za-z][A-Za-z0-9_]*$/
 */
@JvmInline
value class ResourceName(private val s: String) {
    init {
        if (s.isEmpty()) throw IllegalArgumentException("Resource Name must be at least 1 character.")
        if (!s.matches(Regex("^[A-Za-z][A-Za-z0-9_]*$"))) throw IllegalArgumentException("Resource Name '$s' does not match /^[A-Za-z][A-Za-z0-9_]*$/.")
    }
}