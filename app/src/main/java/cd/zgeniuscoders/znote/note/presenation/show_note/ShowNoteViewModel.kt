package cd.zgeniuscoders.znote.note.presenation.show_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowNoteViewModel(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId = savedStateHandle.toRoute<Routes.ShowNote>().noteId

    private var _state = MutableStateFlow(ShowNoteState())

    val state = _state.onStart {
        getNote(noteId)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onTriggerEvent(event: ShowNoteEvent){

    }

    private fun getNote(noteId: Int) {
        viewModelScope.launch {
            noteRepository
                .getNote(noteId)
                .onEach { note ->

                    _state.update {
                        it.copy(
                            note = note
                        )
                    }

                }.launchIn(viewModelScope)
        }
    }

}