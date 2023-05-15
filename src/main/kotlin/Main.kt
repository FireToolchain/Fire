fun main(args: Array<String>) {
    println(tokenize("fn awesome(param:Type):Output{return 2+2;}").list.joinToString(" ") { it.type.toString() })
}