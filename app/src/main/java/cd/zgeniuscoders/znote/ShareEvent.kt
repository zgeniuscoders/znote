package cd.zgeniuscoders.znote

sealed interface ShareEvent {

    data class OnEditNote(
        val noteId: String,
        val content: String,
        val title: String
    ) : ShareEvent

    data class OnAddNote(
        val content: String,
        val title: String
    ) : ShareEvent

}