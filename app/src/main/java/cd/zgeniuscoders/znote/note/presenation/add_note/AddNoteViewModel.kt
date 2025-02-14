package cd.zgeniuscoders.znote.note.presenation.add_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.znote.note.domain.models.NoteRequest
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private var _state = MutableStateFlow(AddNoteState())
    val state = _state.onStart {

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onTriggerEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.OnContentChange -> _state.update { it.copy(content = event.content) }
            AddNoteEvent.OnSaveNote -> addNote()
            is AddNoteEvent.OnTitleChange -> _state.update { it.copy(content = event.title) }
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

    private fun addNote() {
        viewModelScope.launch {
            if (validate()) {

                noteRepository
                    .addNote(
                        NoteRequest(
                            title = state.value.title,
                            content = state.value.content,
                            createdAt = 12
                        )
                    )

                _state.update {
                    it.copy(isAdded = true)
                }

            } else {
                _state.update {
                    it.copy(flashMessage = "Veuillez remplir tout les champs", isAdded = false)
                }
            }
        }
    }

}