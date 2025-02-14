package cd.zgeniuscoders.znote.note.presenation.home

import cd.zgeniuscoders.znote.note.domain.models.Note

data class HomeState(
    val flashMessage: String = "",
    val notes: List<Note> = emptyList()
)
