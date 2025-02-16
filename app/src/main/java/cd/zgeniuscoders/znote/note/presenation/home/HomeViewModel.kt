package cd.zgeniuscoders.znote.note.presenation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.note.data.mappers.toNoteListModel
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
            SharingStarted.Eagerly,
            _state.value
        )


    fun onTriggerEvent(event: HomeEvent){

    }

    private fun getNotes() {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "")
            }

            noteRepository
                .getNotes()
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

}