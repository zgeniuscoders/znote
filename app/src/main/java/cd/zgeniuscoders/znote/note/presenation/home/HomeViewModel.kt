package cd.zgeniuscoders.znote.note.presenation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    var state = _state
        .onStart {
            getNotes()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onTriggerEvent(event: HomeEvent){

    }

    private fun getNotes() {
        viewModelScope.launch {
            noteRepository
                .getNotes()
                .onEach { notes ->

                    _state.update {
                        it.copy(notes = notes)
                    }

                }.launchIn(viewModelScope)
        }
    }

}