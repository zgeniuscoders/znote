package cd.zgeniuscoders.znote.note.presenation.edit_note

sealed interface EditNoteEvent {


    data class OnTitleChange(val title: String): EditNoteEvent
    data class OnContentChange(val content: String): EditNoteEvent

    data object OnSaveNote: EditNoteEvent
}