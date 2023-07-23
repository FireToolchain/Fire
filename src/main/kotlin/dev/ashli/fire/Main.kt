package dev.ashli.fire

import dev.ashli.fire.parser.Program
import dev.ashli.fire.resources.ResourceName
import dev.ashli.fire.tokenizer.tokenize
import java.io.File

/**
 * Main function, entry point of Kindling.
 * This function manages CLI arguments.
 * @param args The command line arguments.
 */
fun main(args: Array<String>) {
    val arguments = CommandLineArguments()
    val iter = args.iterator()
    // This loop goes through each argument in `args` and parses it into a `CommandLineArguments()`
    for(arg in iter) {
        when(arg) {
            "-h", "--help" -> {
                displayHelp()
                return
            }
            "-c", "--code-client" -> {
                arguments.addArgument(CLIArg.CodeClient())
            }
            "-r", "--recode" -> {
                arguments.addArgument(CLIArg.Recode())
            }
            "--emit" -> {
                val next = iter.next()
                if(next == "tokens" || next == "ast1" || next == "ast2" || next == "kindling") {
                    arguments.addArgument(CLIArg.Emit(next))
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
            "build" -> {
                arguments.addArgument(CLIArg.Build())
            }
            else -> {
                println("Invalid option `$arg`!")
                displayHelp()
                return
            }
        }
    }

    println(
        arguments.toString()
    )

    if(arguments.hasArgument(CLIArg.Build())) {
        println("Error: Expected the subcommand `build`.")
        return
    }

    processScripts(arguments)
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
    build                Run the current Fire directory. Reads from `fire/`
    
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

fun processScripts(arguments: CommandLineArguments) {
    val root = File("./fire/")
    val files = walkScripts(root)
    val program = Program(files, arguments)
    println("$program")
}

fun walkScripts(root: File): Array<File> {
    var files: Array<File> = arrayOf()
    root.walkTopDown().forEach { file ->
        if (file.isFile) {
            println("It's a file! $file")
            files = files.plus(file)
        } else {
            println("It's a dir! $file")
        }
    }
    return files
}