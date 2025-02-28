package cd.zgeniuscoders.znote.note.presenation.search_note

sealed interface SearchNoteEvent {
    data class OnSearchTextChanged(val query: String) : SearchNoteEvent
}