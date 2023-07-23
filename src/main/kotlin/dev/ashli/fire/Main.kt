package dev.ashli.fire

import dev.ashli.fire.resources.ResourceName
import dev.ashli.fire.tokenizer.tokenize

fun main(args: Array<String>) {
    val arguments = CommandLineArguments()
    val iter = args.iterator()
    for(arg in iter) {
        when(arg) {
            "-h" -> {
                displayHelp()
                return
            }
            "-c" -> {
                arguments.addArgument(CLIArg.CodeClient())
            }
            "-r" -> {
                arguments.addArgument(CLIArg.Recode())
            }
            "--emit" -> {
                val next = iter.next()
                if(next == "tokens" || next == "ast1" || next == "ast2" || next == "kindling") {
                    arguments.addArgument(CLIArg.Emit("next"))
                } else {
                    println("Error: Invalid emit target. Expected one of: `tokens`, `ast1`, `ast2`, `kindling`.")
                    return
                }
            }
            "--verbose" -> {
                arguments.addArgument(CLIArg.Verbose())
            }
            "--test" -> {
                when(iter.next()) {
                    "token-test" -> {
                        tokenTest()
                    }
                    else -> {
                        println("Error: Invalid test. Expected one of: `token-test`")
                    }
                }
                return
            }
            else -> {
                println("Invalid option `$arg`!")
                displayHelp()
                return
            }
        }
    }
}

fun displayHelp() {
    println("""
Usage: java -jar fire.jar [options] [input]

Options:
    -h, --help           Display this message
    -c, --code-client    Compile with CodeClient
    -r, --recode         Compile with Recode
    --emit (tokens|ast1|ast2|kindling) 
                         Change what the compiler emits.
                         Use `tokens` to emit the tokenized version,
                         `ast1` to emit the first AST,
                         `ast2` to emit the second AST,
                         `kindling` to emit the raw Kindling.
    --verbose            Enable verbose output for debugging.
    --test (token-test)  
                         Run a testing function. (Temporary for developers)
   
Input:
    run                  Run the current Fire directory. Reads from `fire/`
    
""")

}
fun tokenTest() {
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