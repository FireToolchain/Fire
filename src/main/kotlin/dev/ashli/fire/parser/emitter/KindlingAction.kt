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
     * @param arguments Arguments to the code-block.
     */
    class PlayerAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents an "entity-action" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param arguments Arguments to the code-block.
     */
    class EntityAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "game-action" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param arguments Arguments to the code-block.
     */
    class GameAction(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "set-var" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param arguments Arguments to the code-block.
     */
    class SetVar(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "control" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param arguments Arguments to the code-block.
     */
    class Control(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "call" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param arguments Arguments to the code-block.
     */
    class Call(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()
    /**
     * This represents a "start" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param arguments Arguments to the code-block.
     */
    class Start(val type: String, val selector: String, val arguments: Array<KindlingArgument>) : KindlingAction()

    /**
     * This represents an "if-player" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param invert Either "norm" or "not" depending on the inversion of the statement.
     * @param arguments Arguments to the code-block.
     * @param code Code to run if it is true.
     * @param elseCode Code to run otherwise.
     */
    class IfPlayer(
        val type: String,
        val selector: String,
        val invert: String,
        val arguments: Array<KindlingArgument>,
        val code: Array<KindlingAction>,
        val elseCode: Array<KindlingAction>?
    ) : KindlingAction()
    /**
     * This represents an "if-entity" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param selector This represents whose receiving the action.
     * @param invert Either "norm" or "not" depending on the inversion of the statement.
     * @param arguments Arguments to the code-block.
     * @param code Code to run if it is true.
     * @param elseCode Code to run otherwise.
     */
    class IfEntity(
        val type: String,
        val selector: String,
        val invert: String,
        val arguments: Array<KindlingArgument>,
        val code: Array<KindlingAction>,
        val elseCode: Array<KindlingAction>?
    ) : KindlingAction()

    /**
     * This represents an "if-var" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param invert Either "norm" or "not" depending on the inversion of the statement.
     * @param arguments Arguments to the code-block.
     * @param code Code to run if it is true.
     * @param elseCode Code to run otherwise.
     */
    class IfVariable(
        val type: String,
        val invert: String,
        val arguments: Array<KindlingArgument>,
        val code: Array<KindlingAction>,
        val elseCode: Array<KindlingAction>?
    ) : KindlingAction()
    /**
     * This represents an "if-game" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param invert Either "norm" or "not" depending on the inversion of the statement.
     * @param arguments Arguments to the code-block.
     * @param code Code to run if it is true.
     * @param elseCode Code to run otherwise.
     */
    class IfGame(
        val type: String,
        val invert: String,
        val arguments: Array<KindlingArgument>,
        val code: Array<KindlingAction>,
        val elseCode: Array<KindlingAction>?
    ) : KindlingAction()
    /**
     * This represents a "repeat" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param subType This represents the "sub-action" of the block, normally below the "action".
     * @param invert Either "norm" or "not" depending on the inversion of the statement.
     * @param arguments Arguments to the code-block.
     * @param code Code to run if it is true.
     */
    class Repeat(
        val type: String,
        val subType: String,
        val invert: String,
        val arguments: Array<KindlingArgument>,
        val code: Array<KindlingAction>,
    ) : KindlingAction()
    /**
     * This represents a "select-object" in Kindling.
     * @param type This represents the "action" of the block, for example, "SendMessage" on a Player Action.
     * @param subType This represents the "sub-action" of the block, normally below the "action".
     * @param invert Either "norm" or "not" depending on the inversion of the statement.
     * @param arguments Arguments to the code-block.
     */
    class SelectObject(
        val type: String,
        val subType: String,
        val invert: String,
        val arguments: Array<KindlingArgument>,
    ) : KindlingAction()
    /**
     * This represents a "ret" in Kindling.
     * @param value The value to return.
     */
    class Return(val value: KindlingArgument?) : KindlingAction()
    /**
     * This represents a "yield" in Kindling.
     * @param value The value to yield with.
     */
    class Yield(val value: KindlingArgument) : KindlingAction()

}
/**
 * This function allows you to emit one action into a KindlingValue.
 *
 * @param self The KindlingAction to transform into a KindlingValue.
 * @return Returns a KindlingValue, specifically a KindlingValue.List with the action's details provided.
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
        is KindlingAction.IfPlayer -> {
            self.elseCode?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("if-player"),
                    KindlingValue.Text(self.type),
                    KindlingValue.Identifier(self.selector),
                    KindlingValue.Identifier(self.invert),
                    KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                    KindlingValue.List(emitActions(self.code)),
                    KindlingValue.List(emitActions(self.elseCode)),
                ))
            }
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("if-player"),
                KindlingValue.Text(self.type),
                KindlingValue.Identifier(self.selector),
                KindlingValue.Identifier(self.invert),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                KindlingValue.List(emitActions(self.code)),
            ))
        }
        is KindlingAction.IfEntity -> {
            self.elseCode?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("if-entity"),
                    KindlingValue.Text(self.type),
                    KindlingValue.Identifier(self.selector),
                    KindlingValue.Identifier(self.invert),
                    KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                    KindlingValue.List(emitActions(self.code)),
                    KindlingValue.List(emitActions(self.elseCode)),
                ))
            }
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("if-entity"),
                KindlingValue.Text(self.type),
                KindlingValue.Identifier(self.selector),
                KindlingValue.Identifier(self.invert),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                KindlingValue.List(emitActions(self.code)),
            ))
        }
        is KindlingAction.IfVariable -> {
            self.elseCode?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("if-var"),
                    KindlingValue.Text(self.type),
                    KindlingValue.Identifier(self.invert),
                    KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                    KindlingValue.List(emitActions(self.code)),
                    KindlingValue.List(emitActions(self.elseCode)),
                ))
            }
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("if-var"),
                KindlingValue.Text(self.type),
                KindlingValue.Identifier(self.invert),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                KindlingValue.List(emitActions(self.code)),
            ))
        }
        is KindlingAction.IfGame -> {
            self.elseCode?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("if-game"),
                    KindlingValue.Text(self.type),
                    KindlingValue.Identifier(self.invert),
                    KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                    KindlingValue.List(emitActions(self.code)),
                    KindlingValue.List(emitActions(self.elseCode)),
                ))
            }
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("if-game"),
                KindlingValue.Text(self.type),
                KindlingValue.Identifier(self.invert),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                KindlingValue.List(emitActions(self.code)),
            ))
        }
        is KindlingAction.Repeat -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("repeat"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.subType),
                KindlingValue.Identifier(self.invert),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
                KindlingValue.List(emitActions(self.code)),
            ))
        }
        is KindlingAction.SelectObject -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("select-object"),
                KindlingValue.Text(self.type),
                KindlingValue.Text(self.subType),
                KindlingValue.Identifier(self.invert),
                KindlingValue.List(self.arguments.map { x -> emitArgument(x) }.toTypedArray()),
            ))
        }
        is KindlingAction.Return -> {
            self.value?.let {
                return KindlingValue.List(arrayOf(
                    KindlingValue.Identifier("ret"),
                    emitArgument(self.value)
                ))
            }
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("ret"),
            ))
        }
        is KindlingAction.Yield -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("yield"),
                emitArgument(self.value)
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
 * @return Returns a KindlingValue, specifically a KindlingValue.List with the action's details provided.
 * @see KindlingValue
 */
fun emitActions(self: Array<KindlingAction>): Array<KindlingValue> {
    var out: Array<KindlingValue> = arrayOf()
    for(i in self) {
        out += emitAction(i)
    }
    return out
}