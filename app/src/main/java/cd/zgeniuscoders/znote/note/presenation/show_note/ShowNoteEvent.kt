package cd.zgeniuscoders.znote.note.presenation.show_note

sealed interface ShowNoteEvent {

    data object OnDeleteNote: ShowNoteEvent

}