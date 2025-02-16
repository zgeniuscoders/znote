package cd.zgeniuscoders.znote.note.domain.repository

import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.note.data.dto.NoteDto
import cd.zgeniuscoders.znote.note.data.dto.NotesDto
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.domain.models.NoteRequest
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getNotes(): Flow<Resource<NotesDto>>

    suspend fun getNote(noteId: Int): Flow<Resource<NoteDto>>

    suspend fun addNote(note: NoteRequest): Flow<Resource<Boolean>>

    suspend fun deleteNote(note: Note): Flow<Resource<Boolean>>

    suspend fun updateNote(noteId: Int, note: Note): Flow<Resource<Boolean>>

}