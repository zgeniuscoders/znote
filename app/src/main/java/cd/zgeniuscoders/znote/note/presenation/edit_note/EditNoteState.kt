package cd.zgeniuscoders.znote.note.presenation.edit_note

data class EditNoteState(
    val flashMessage: String = "",
    val title: String = "",
    val content: String = "",
    val createdAt: Long = 0,
    val isUpdated: Boolean = false,
    val noteId: Int = 0
)
