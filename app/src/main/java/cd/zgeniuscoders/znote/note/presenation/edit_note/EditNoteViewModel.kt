package cd.zgeniuscoders.znote.note.presenation.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.note.data.mappers.toNoteModel
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
import java.util.Date

class EditNoteViewModel(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = MutableStateFlow(EditNoteState())
    private val noteId = savedStateHandle.toRoute<Routes.ShowNote>().noteId

    val state = _state.onStart {
        getNote()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onTriggerEvent(event: EditNoteEvent) {
        when (event) {
            is EditNoteEvent.OnContentChange -> _state.update { it.copy(content = event.content) }
            EditNoteEvent.OnSaveNote -> updateNote()
            is EditNoteEvent.OnTitleChange -> _state.update { it.copy(title = event.title) }
        }
    }

    private fun validate(): Boolean {
        return if (state.value.title.trim().isEmpty()) {
            false
        } else if (state.value.content.trim().isEmpty()) {
            false
        } else {
            true
        }
    }

    private fun getNote() {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "")
            }

            noteRepository
                .getNote(noteId)
                .onEach { res ->

                    when (res) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(flashMessage = res.message.toString())
                            }
                        }

                        is Resource.Success -> {
                            if (res.data != null) {
                                val note = res.data.toNoteModel()
                                _state.update {
                                    it.copy(
                                        content = note.content,
                                        title = note.title,
                                        noteId = note.id
                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(flashMessage = "An excepted error occurred")
                                }
                            }
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }

    private fun updateNote() {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "")
            }

            if (validate()) {

                noteRepository
                    .updateNote(
                        state.value.noteId,
                        Note(
                            id = state.value.noteId,
                            title = state.value.title,
                            content = state.value.content,
                            createdAt = Date().time
                        )
                    ).onEach { res ->

                        when (res) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(flashMessage = res.message.toString())
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(isUpdated = true)
                                }
                            }
                        }

                    }.launchIn(viewModelScope)

            } else {
                _state.update {
                    it.copy(flashMessage = "Veuillez remplir tout les champs", isUpdated = false)
                }
            }
        }
    }

}