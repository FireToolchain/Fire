package dev.ashli.fire.parser.emitter

/**
 * Inside the Kindling headers, there is a list of actions. This class represents each one of those actions.
 *
 * @see KindlingArgument
 */
sealed class KindlingAction {
    /**
     * This represents a "player-action" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class PlayerAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents an "entity-action" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class EntityAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "game-action" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class GameAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "set-var" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class SetVar(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "control" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class Control(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "call" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class Call(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "start" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     */
    class Start(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()

}
/**
 * This function allows you to emit one action into a KindlingValue.
 *
 * @param self The KindlingAction to transform into a KindlingValue.
 * @return Returns a KindlingValue, specifically a KindlingValue.List with the actions's details provided.
 * @see KindlingValue
 */
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
        is KindlingAction.GameAction -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("game-action"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }
        is KindlingAction.SetVar -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("set-var"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }
        is KindlingAction.Control -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("control"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }
        is KindlingAction.Call -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("call"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }
        is KindlingAction.Start -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("start"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.selector),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray())
            ))
        }

    }
}
/**
 * This function allows you to emit the actions into a KindlingValue.
 * This is used internally because it's a quick and easy way to transform the Array,
 * instead of manually looping through it each time.
 *
 * @param self The KindlingActions to transform into a KindlingValue.
 * @return Returns a KindlingValue, specifically a KindlingValue.List with the actions's details provided.
 * @see KindlingValue
 */
fun emitActions(self: Array<KindlingAction>): Array<KindlingValue> {
    var out: Array<KindlingValue> = arrayOf()
    for(i in self) {
        out += emitAction(i)
    }
    return out
}