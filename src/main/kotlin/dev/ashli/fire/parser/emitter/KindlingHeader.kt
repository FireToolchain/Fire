package dev.ashli.fire.parser.emitter

sealed class KindlingHeader {
    class PlayerEvent(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    class EntityEvent(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    class Function(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
    class Process(val name: String, val code: Array<KindlingAction>) : KindlingHeader()
}

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