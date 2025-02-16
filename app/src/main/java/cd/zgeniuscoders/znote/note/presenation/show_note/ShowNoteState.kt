package cd.zgeniuscoders.znote.note.presenation.show_note

import cd.zgeniuscoders.znote.note.domain.models.Note

data class ShowNoteState(
    val note: Note? = null,
    val flashMessage: String = "",
    val isNoteDeleted: Boolean = false
)
