package cd.zgeniuscoders.znote.note.data.mappers

import cd.zgeniuscoders.znote.note.data.dto.NoteDto
import cd.zgeniuscoders.znote.note.data.dto.NotesDto
import cd.zgeniuscoders.znote.note.data.toDateTime
import cd.zgeniuscoders.znote.note.domain.models.Note

fun NoteDto.toNoteModel(): Note {
    return Note(
        id = data.id,
        title = data.title,
        content = data.content,
        createdAt = toDateTime(data.createdAt),
        isDelete = data.isDelete
    )
}

fun NotesDto.toNoteListModel(): List<Note> {
    return data.map {
        Note(
            id = it.id,
            title = it.title,
            content = it.content,
            createdAt = toDateTime(it.createdAt),
            isDelete = it.isDelete
        )
    }
}