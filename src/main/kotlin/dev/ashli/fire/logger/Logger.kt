package dev.ashli.fire.logger

/**
 * Logs a message to console.
 * @param msg Message to log.
 */
fun logMsg(vararg msg: String?) {
    println(msg.joinToString(""))
}

/**
 * Logs a warning message to console.
 * @param msg Message to log.
 */
fun logWarning(vararg msg: String?) {
    println(msg.joinToString(""))
}

/**
 * Logs an error message to console.
 * @param msg Message to log.
 */
fun logError(vararg msg: String?) {
    println(msg.joinToString(""))
}

/**
 * Logs a success message to console.
 * @param msg Message to log.
 */
fun logSuccess(vararg msg: String?) {
    println(msg.joinToString(""))
}