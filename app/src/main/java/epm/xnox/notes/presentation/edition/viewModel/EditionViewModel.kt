package epm.xnox.notes.presentation.edition.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.notes.data.sources.database.entities.NoteEntity
import epm.xnox.notes.domain.model.Note
import epm.xnox.notes.domain.usecase.DeleteNoteUseCase
import epm.xnox.notes.domain.usecase.GetDateUseCase
import epm.xnox.notes.domain.usecase.GetNoteUseCase
import epm.xnox.notes.domain.usecase.InsertNoteUseCase
import epm.xnox.notes.domain.usecase.UpdateNoteUseCase
import epm.xnox.notes.presentation.edition.ui.EditionEvent
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditionViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getDateUseCase: GetDateUseCase
) : ViewModel() {

    private val _state = mutableStateOf(Note())
    val state: State<Note> = _state

    fun onEvent(event: EditionEvent) {
        when (event) {
            EditionEvent.SaveNote -> {
                if (_state.value.id == 0) insertNote()
                else updateNote()
            }

            is EditionEvent.DeleteNote -> deleteNote()

            is EditionEvent.GetNote -> getNote(event.id)

            is EditionEvent.SetContent -> _state.value =
                _state.value.copy(content = event.content)

            is EditionEvent.SetTitle -> _state.value =
                _state.value.copy(title = event.title)

            is EditionEvent.SetMark -> _state.value =
                _state.value.copy(mark = event.mark)
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            deleteNoteUseCase.invoke(_state.value.id)
        }
    }

    private fun updateNote() {
        val note = NoteEntity(
            id = _state.value.id,
            date = _state.value.date,
            title = _state.value.title,
            content = _state.value.content,
            mark = _state.value.mark
        )
        viewModelScope.launch {
            updateNoteUseCase.invoke(note)
        }
    }

    private fun insertNote() {
        val note = NoteEntity(
            date = getDateUseCase.invoke(),
            title = _state.value.title,
            content = _state.value.content,
            mark = _state.value.mark
        )
        viewModelScope.launch {
            insertNoteUseCase.invoke(note)
        }
    }

    private fun getNote(id: Int) {
        viewModelScope.launch {
            getNoteUseCase.invoke(id)
                .take(1)
                .collect { note ->
                    _state.value = note
                }
        }
    }
}