package cd.zgeniuscoders.znote.note.data.repository

import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.note.data.dto.NoteDto
import cd.zgeniuscoders.znote.note.data.dto.NotesDto
import cd.zgeniuscoders.znote.note.data.local.NoteDaoService
import cd.zgeniuscoders.znote.note.data.local.NoteEntity
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.domain.models.NoteRequest
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RoomNoteRepository(
    private val noteDaoService: NoteDaoService
) : NoteRepository {

    override suspend fun getNotes(): Flow<Resource<NotesDto>> = callbackFlow {

        try {
            val notes = noteDaoService
                .all()
                .map { noteEntities ->

                    noteEntities.toNoteDtoData()
                }


            trySend(
                Resource.Success(
                    NotesDto(
                        notes
                    )
                )
            )


        } catch (e: NullPointerException) {
            e.printStackTrace()
            trySend(
                Resource.Error(
                    e.message.toString()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(
                Resource.Error(
                    e.message.toString()
                )
            )
        }

        awaitClose()

    }

    override suspend fun getNote(noteId: Int): Flow<Resource<NoteDto>> = callbackFlow {

        try {


            val note = noteDaoService
                .show(noteId)

            if (note != null) {
                trySend(
                    Resource.Success(
                        NoteDto(
                            note.toNoteDtoData()
                        )
                    )
                )
            } else {

                trySend(
                    Resource.Error(
                        "Unexpected error occurred"
                    )
                )
            }


        } catch (e: Exception) {
            e.printStackTrace()
            trySend(
                Resource.Error(
                    e.message.toString()
                )
            )
        }

        awaitClose()
    }

    override suspend fun addNote(note: NoteRequest): Flow<Resource<Boolean>> = callbackFlow {

        try {
            noteDaoService
                .add(NoteEntity.fromModelRequest(note))

            trySend(
                Resource.Success(
                    true
                )
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
            trySend(
                Resource.Error(
                    e.message.toString()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(
                Resource.Error(
                    e.message.toString()
                )
            )
        }

        awaitClose()

    }

    override suspend fun deleteNote(note: Note): Flow<Resource<Boolean>> = callbackFlow {

        try {
            noteDaoService
                .delete(NoteEntity.fromModel(note))

            trySend(
                Resource.Success(
                    true
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(
                Resource.Error(
                    e.message.toString()
                )
            )
        }

        awaitClose()
    }

    override suspend fun updateNote(noteId: Int, note: Note): Flow<Resource<Boolean>> =
        callbackFlow {

            try {
                noteDaoService
                    .add(NoteEntity.fromModel(note))
                trySend(
                    Resource.Success(
                        true
                    )
                )
            } catch (e: NullPointerException) {
                e.printStackTrace()
                trySend(
                    Resource.Error(
                        e.message.toString()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                trySend(
                    Resource.Error(
                        e.message.toString()
                    )
                )
            }

            awaitClose()

    }
}