package cd.zgeniuscoders.znote.note.presenation.delete_notes

import cd.zgeniuscoders.znote.note.domain.models.Note

data class DeleteNoteState(
    val flashMessage: String = "",
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList()
)
