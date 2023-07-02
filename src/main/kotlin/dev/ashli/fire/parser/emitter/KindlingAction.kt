package dev.ashli.fire.parser.emitter

sealed class KindlingAction {
    class PlayerAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    class EntityAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
}

fun emitAction(self: KindlingAction): KindlingValue {
    return when(self) {
        is KindlingAction.PlayerAction -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("player-action"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }
        is KindlingAction.EntityAction -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("entity-action"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }
    }
}

fun emitActions(self: Array<KindlingAction>): Array<KindlingValue> {
    var out: Array<KindlingValue> = arrayOf()
    for(i in self) {
        out += emitAction(i)
    }
    return out
}