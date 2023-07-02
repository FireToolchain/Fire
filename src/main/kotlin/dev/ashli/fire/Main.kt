package dev.ashli.fire

import dev.ashli.fire.parser.emitter.*

fun main(args: Array<String>) {

    val toEmit = KindlingHeader.PlayerEvent("join", arrayOf(
        KindlingAction.PlayerAction("SendMessage", "", arrayOf(KindlingArgument.Text("hi %default"))),
    ))

    val output = emitValue(emitHeader(toEmit))
    println(output)

    return
//    println(
//        tokenize(
//            """
//        fn awesome(param: Int): Output {
//            let! a = 2;
//            let! b = param;
//            return "Your answer is:\n" & a + b;
//        }
//    """,
//            ResourceName("resource.fire")
//        ).list.joinToString("\n") { it.type.toString() })
}