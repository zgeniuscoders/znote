package cd.zgeniuscoders.znote.note.presenation.add_note

sealed interface AddNoteEvent {


    data class OnTitleChange(val title: String): AddNoteEvent
    data class OnContentChange(val content: String): AddNoteEvent
    data object OnLaunchSpeechNote: AddNoteEvent

    data object OnSaveNote: AddNoteEvent
}