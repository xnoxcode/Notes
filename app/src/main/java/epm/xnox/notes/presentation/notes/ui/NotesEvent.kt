package epm.xnox.notes.presentation.notes.ui

sealed class NotesEvent {
    data object DeleteAllNotes : NotesEvent()
    data object GetAllNotes : NotesEvent()
}