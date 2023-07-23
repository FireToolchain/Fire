package dev.ashli.fire

/**
 * This class represents a list of command line arguments.
 */
class CommandLineArguments {
    private val arguments: Array<CLIArg> = arrayOf()

    /**
     * Check if the CLI has an argument.
     * @param arg Argument to check if it exists or not.
     */
    fun hasArgument(arg: CLIArg): Boolean {
        return arguments.contains(arg)
    }

    /**
     * Append an argument to the current list of arguments.
     * Does not add anything if the array already contains the argument.
     * @param arg Argument to append.
     */
    fun addArgument(arg: CLIArg) {
        if(!arguments.contains(arg)) {
            arguments.plus(arg)
        }
    }
}

/**
 * Represents a single argument on the command line.
 */
open class CLIArg {
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
     * Represents the `run` input.
     */
    class Run : CLIArg()

    /**
     * Represents the `--emit` option.
     * @param emit Must be one of the following: `tokens`, `ast1`, `ast2`, `kindling`
     */
    class Emit(private val emit: String) : CLIArg()

    /**
     * Represents the `--verbose` option.
     */
    class Verbose : CLIArg()
}
