package cd.zgeniuscoders.znote.note.presenation.search_note

import cd.zgeniuscoders.znote.note.domain.models.Note

data class SearchNoteState(
    val isSearch: Boolean = false,
    val notes: List<Note> = emptyList(),
    val searchNotes: List<Note> = emptyList(),
    val flashMessage: String = "",
    val searchQuery: String = ""
)
