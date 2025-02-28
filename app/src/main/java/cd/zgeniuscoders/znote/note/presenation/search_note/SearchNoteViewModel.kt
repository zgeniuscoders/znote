package cd.zgeniuscoders.znote.note.presenation.search_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.note.data.mappers.toNoteListModel
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchNoteState())

    @OptIn(FlowPreview::class)
    var state = _state
        .debounce(500L)
        .onEach { _state.update { it.copy(isSearch = true) } }
        .combine(_state) { _, state ->

            if (state.searchQuery.isBlank()) {
                state
            } else {
                state.copy(
                    searchNotes = state.notes.filter {
                        it.doesMatchSearchQuery(state.searchQuery)
                    }
                )
            }

        }
        .onEach { _state.update { it.copy(isSearch = false) } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    init {
        getNotes()
    }

    fun onTriggerEvent(event: SearchNoteEvent) {
        when (event) {
            is SearchNoteEvent.OnSearchTextChanged -> _state.update { it.copy(searchQuery = event.query) }
        }
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