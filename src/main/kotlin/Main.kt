import tokenizing.tokenize

fun main(args: Array<String>) {
    println(
        tokenize("""
        fn awesome(param: Int): Output {
            let! a = 2;
            let! b = param;
            return "Your answer is:\n" & a + b;
        }
    """).list.joinToString("\n") { it.type.toString() })
}