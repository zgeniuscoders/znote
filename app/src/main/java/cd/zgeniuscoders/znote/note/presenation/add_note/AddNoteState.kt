package cd.zgeniuscoders.znote.note.presenation.add_note

data class AddNoteState(
    val flashMessage: String = "",
    val title: String = "",
    val content: String = "",
    val createdAt: Long = 0,
    val isAdded: Boolean = false
)
