package epm.xnox.notes.presentation.notes.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.notes.domain.model.Note
import epm.xnox.notes.domain.usecase.DeleteAllNotesUseCase
import epm.xnox.notes.domain.usecase.GetAllNotesUseCase
import epm.xnox.notes.presentation.notes.ui.NotesEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase
) : ViewModel() {

    private val _state = mutableStateOf<List<Note>>(emptyList())
    val state: State<List<Note>> = _state

    init {
        onEvent(NotesEvent.GetAllNotes)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            NotesEvent.DeleteAllNotes -> deleteAllNotes()
            is NotesEvent.GetAllNotes -> getAllNotes()
        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.invoke().collect { notes ->
                _state.value = notes
            }
        }
    }

    private fun deleteAllNotes() {
        viewModelScope.launch {
            deleteAllNotesUseCase.invoke()
        }
    }
}