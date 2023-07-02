package dev.ashli.fire.parser.emitter

/**
 * In Kindling, each program has a set of headers. In DF, these translate to Player Events, Entity Events,
 * Functions, or Processes.
 *

 * @see KindlingAction
 */
sealed class KindlingHeader {
    /**
     * This represents the "player-event" header in Kindling.
     * When mapped out to DF, it is a player event line.
     * @param name This is the name of the event/function/process.
     * @param code This represents the actions of the event/function/process.
     */
    class PlayerEvent(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    /**
     * This represents the "entity-event" header in Kindling.
     * When mapped out to DF, it is a entity event line.
     * @param name This is the name of the event/function/process.
     * @param code This represents the actions of the event/function/process.
     */
    class EntityEvent(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    /**
     * This represents the "def" header in Kindling.
     * When mapped out to DF, it is a function line.
     * @param name This is the name of the event/function/process.
     * @param code This represents the actions of the event/function/process.
     */
    class Function(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    /**
     * This represents the "process" header in Kindling.
     * When mapped out to DF, it is a process line.
     * @param name This is the name of the event/function/process.
     * @param code This represents the actions of the event/function/process.
     */
    class Process(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
}

/**
* This function allows you to emit the headers into a KindlingValue.
 *
 * @param self The KindlingHeader to transform into a KindlingValue.
 * @return Returns a KindlingValue, specifically a KindlingValue.List with the header's details provided.
 * @see KindlingHeader
 * @see KindlingValue
 */
fun emitHeader(self: KindlingHeader): KindlingValue {
    return when(self) {
        is KindlingHeader.PlayerEvent -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("player-event"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
        is KindlingHeader.EntityEvent -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("entity-event"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
        is KindlingHeader.Function -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("def"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
        is KindlingHeader.Process -> {
            KindlingValue.List(arrayOf(
                KindlingValue.Identifier("process"),
                KindlingValue.Text(self.name),
                KindlingValue.List(emitActions(self.code))
            ))
        }
    }

}