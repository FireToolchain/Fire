package dev.ashli.fire

import dev.ashli.fire.parser.emitter.*
import dev.ashli.fire.resources.ResourceName
import dev.ashli.fire.tokenizer.tokenize

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        displayHelp()
        return
    }
    when(args[0]) {
        else -> {
            displayHelp()
        }
    }
}

// test

fun displayHelp() {
    println("""
Usage: fire [options] input

Options:
    -h, --help           Display this message
    -c, --code-client    Compile with CodeClient
    -r, --recode         Compile with Recode
    --emit [tokens|ast1|ast2|kindling]  
                         Change what the compiler emits.
                         Use `tokens` to emit the tokenized version,
                         `ast1` to emit the first AST,
                         `ast2` to emit the second AST,
                         `kindling` to emit the raw Kindling.
    --verbose            Enable verbose output for debugging.
    
""")

}
fun token_test() {
    println(
        tokenize(
            """
        fn awesome(param: Int): Output {
            let! a = 2;
            let! b = param;
            return "Your answer is:\n" & a + b;
        }
    """, ResourceName("f.fire")
        ).list.joinToString("\n") { it.type.toString() })
}