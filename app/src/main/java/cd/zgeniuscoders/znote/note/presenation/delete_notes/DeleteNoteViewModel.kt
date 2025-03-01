package cd.zgeniuscoders.znote.note.presenation.delete_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.note.data.mappers.toNoteListModel
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeleteNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DeleteNoteState())
    var state = _state
        .onStart {
            getNotes()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value
        )


    fun onTriggerEvent(event: DeleteNoteEvent) {
        when (event) {
            is DeleteNoteEvent.OnDeleteNote -> deleteNote(event.note)
            is DeleteNoteEvent.OnRestoreNote -> restoreNote(event.note)
        }
    }

    private fun restoreNote(note: Note) {
        viewModelScope.launch {

            _state.update { it.copy(flashMessage = "", isLoading = true) }

            val newNote = note.copy(isDelete = false)
            noteRepository
                .updateNote(newNote.id, newNote)
                .onEach { res ->

                    when (res) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    flashMessage = res.message.toString(),
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Success -> {
                            getNotes()
                            _state.update { it.copy(isLoading = false) }
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }

    private fun getNotes() {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "")
            }

            noteRepository
                .getDeleteNotes()
                .onEach { res ->

                    when (res) {

                        is Resource.Error -> {

                            _state.update {
                                it.copy(flashMessage = res.message.toString())
                            }

                        }

                        is Resource.Success -> {
                            val notes = res.data!!.toNoteListModel()
                            _state.update {
                                it.copy(notes = notes)
                            }

                        }
                    }


                }.launchIn(viewModelScope)
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "",isLoading=true)
            }

            noteRepository
                .deleteNote(
                    note = note
                )
                .onEach { res ->

                    when (res) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(flashMessage = res.message.toString(), isLoading = false)
                            }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(isLoading = false)
                            }
                            getNotes()
                        }
                    }

                }.launchIn(viewModelScope)

        }
    }


}