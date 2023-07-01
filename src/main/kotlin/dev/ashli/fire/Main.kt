package dev.ashli.fire

import dev.ashli.fire.parser.emitter.KindlingValue

fun main(args: Array<String>) {

    val toEmit = KindlingValue.List(arrayOf(
        KindlingValue.Identifier("def"),
        KindlingValue.Text("constants"),
        KindlingValue.List(arrayOf(
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("set-var"),
                KindlingValue.Text("="),
                KindlingValue.List(arrayOf(
                    KindlingValue.List(arrayOf(
                        KindlingValue.Identifier("global"),
                        KindlingValue.Text("firstJoin")
                    )),
                    KindlingValue.List(arrayOf(
                        KindlingValue.Identifier("num"),
                        KindlingValue.Number(1.0)
                    ))
                ))
            )),
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("set-var"),
                KindlingValue.Text("="),
                KindlingValue.List(arrayOf(
                    KindlingValue.List(arrayOf(
                        KindlingValue.Identifier("global"),
                        KindlingValue.Text("clickerItem")
                    )),
                    KindlingValue.List(arrayOf(
                        KindlingValue.Identifier("item"),
                        KindlingValue.EscapedText("""{id:"minecraft:feather",Count:1,tag:{display:{Name:\'{"italic":"false","color":"#FFFFAA","text":"Clicker"}\'}}}""")
                    ))
                ))
            ))
        ))
    ))

    val output = KindlingValue.emitValue(toEmit)
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