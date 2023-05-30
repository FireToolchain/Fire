package dev.ashli.fire.parser.complete.annotations

/**
 * Represents parsed Fire code that can have annotations
 */
interface Annotated {
    val annotations: List<Annotation>
}