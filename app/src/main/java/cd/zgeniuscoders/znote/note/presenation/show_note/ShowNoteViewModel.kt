package cd.zgeniuscoders.znote.note.presenation.show_note

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.znote.Resource
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.note.data.mappers.toNoteModel
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
        Log.i("VN_SCOPE", "on start called")

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onTriggerEvent(event: ShowNoteEvent){
        when (event) {
            ShowNoteEvent.OnDeleteNote -> deleteNote()
        }
    }

    init {
        Log.i("VN_SCOPE", "on init called")
    }

    private fun getNote(noteId: Int) {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "")
            }

            Log.i("VN_SCOPE", "get note called")

            noteRepository
                .getNote(noteId)
                .onEach { res ->

                    when (res) {
                        is Resource.Error -> TODO()
                        is Resource.Success -> {

                            if (res.data != null) {
                                val note = res.data.toNoteModel()
                                _state.update {
                                    it.copy(
                                        note = note
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

    private fun deleteNote() {
        viewModelScope.launch {

            _state.update {
                it.copy(flashMessage = "")
            }

            Log.i("VN_SCOPE", "on delete called")


            noteRepository
                .deleteNote(state.value.note!!)
                .onEach { res ->

                    when (res) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(flashMessage = res.message.toString())
                            }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(isNoteDeleted = true,note = null)
                            }
                        }
                    }

                }.launchIn(viewModelScope)

        }
    }

}