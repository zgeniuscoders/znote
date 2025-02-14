package cd.zgeniuscoders.znote.note.domain.repository

import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.domain.models.NoteRequest
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun getNote(noteId: Int): Flow<Note>

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(noteId: Int, note: Note)

}