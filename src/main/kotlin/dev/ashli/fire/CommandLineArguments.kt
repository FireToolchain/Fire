package dev.ashli.fire

/**
 * This class represents a list of command line arguments.
 */
class CommandLineArguments {
    private var arguments: Array<CLIArg> = arrayOf()

    /**
     * Check if the CLI has an argument.
     * @param arg Argument to check if it exists or not.
     */
    fun hasArgument(arg: CLIArg): Boolean {
        val cmp1 = "${arguments.map { x -> x.toString() }}"
        val cmp2 = "$arg"
        return cmp1.contains(cmp2)
    }

    /**
     * Append an argument to the current list of arguments.
     * Does not add anything if the array already contains the argument.
     * @param arg Argument to append.
     */
    fun addArgument(arg: CLIArg) {
        if(!arguments.contains(arg)) {
            arguments = arguments.plus(arg)
        }
    }

    /**
     * Converts the set of arguments to a string.
     * @return String it was transformed into.
     */
    override fun toString(): String {
        var out = "Flags{"
        for(argument in arguments) {
            out = "$out$argument;"
        }
        out = "$out}"
        return out
    }
}

/**
 * Represents a single argument on the command line.
 */
open class CLIArg {
    override fun toString(): String {
        return when(this) {
            is Verbose -> { "verbose" }
            is Build -> { "build" }
            is Emit -> { "emit(${this.emit})" }
            is Recode -> { "recode" }
            is CodeClient -> { "code-client" }
            is Help -> { "help" }
            else -> { "error" }
        }
    }
    /**
     * Represents the `-h` or `--help` option.
     */
    class Help : CLIArg()

    /**
     * Represents the `-c` or `--code-client` option.
     */
    class CodeClient : CLIArg()

    /**
     * Represents the `-r` or `--recode` option.
     */
    class Recode : CLIArg()

    /**
     * Represents the `build` input.
     */
    class Build : CLIArg()

    /**
     * Represents the `--emit` option.
     * @param emit Must be one of the following: `tokens`, `ast1`, `ast2`, `kindling`
     */
    class Emit(val emit: String) : CLIArg()

    /**
     * Represents the `--verbose` option.
     */
    class Verbose : CLIArg()
}
