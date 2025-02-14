package cd.zgeniuscoders.znote.note.data.repository

import cd.zgeniuscoders.znote.note.data.local.NoteDaoService
import cd.zgeniuscoders.znote.note.data.local.NoteEntity
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.domain.models.NoteRequest
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteRepository(
    private val noteDaoService: NoteDaoService
) : NoteRepository {

    override suspend fun getNotes(): Flow<List<Note>> {

        val notes = noteDaoService
            .all()
            .map { noteEntities -> noteEntities.map { it.toModel() } }

        return notes

    }

    override suspend fun getNote(noteId: Int): Flow<Note> {

        val note = noteDaoService
            .show(noteId)
            .map { noteEntity -> noteEntity.toModel() }

        return note

    }

    override suspend fun addNote(note: NoteRequest) {
        noteDaoService
            .add(NoteEntity.fromModelRequest(note))
    }

    override suspend fun deleteNote(note: Note) {
        noteDaoService
            .delete(NoteEntity.fromModel(note))
    }

    override suspend fun updateNote(noteId: Int, note: Note) {
        noteDaoService
            .add(NoteEntity.fromModel(note))
    }
}