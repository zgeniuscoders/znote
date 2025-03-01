package cd.zgeniuscoders.znote.note.presenation.delete_notes

import cd.zgeniuscoders.znote.note.domain.models.Note

sealed interface DeleteNoteEvent {
    data class OnDeleteNote(val note:Note): DeleteNoteEvent
    data class OnRestoreNote(val note:Note): DeleteNoteEvent
}
