package cd.zgeniuscoders.znote.note.presenation.edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.znote.Routes
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
            is EditNoteEvent.OnTitleChange -> _state.update { it.copy(content = event.title) }
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
            noteRepository
                .getNote(noteId)
                .onEach { note ->

                    _state.update {
                        it.copy(content = note.content, title = note.title, noteId = note.id)
                    }

                }.launchIn(viewModelScope)
        }
    }

    private fun updateNote() {
        viewModelScope.launch {
            if (validate()) {

                noteRepository
                    .updateNote(
                        state.value.noteId,
                        Note(
                            id = state.value.noteId,
                            title = state.value.title,
                            content = state.value.content,
                            createdAt = state.value.createdAt
                        )
                    )

                _state.update {
                    it.copy(isUpdated = true)
                }

            } else {
                _state.update {
                    it.copy(flashMessage = "Veuillez remplir tout les champs", isUpdated = false)
                }
            }
        }
    }

}