package epm.xnox.notes.presentation.notes.ui

sealed class NotesMenu(val name: String) {
    data object Delete : NotesMenu("Eliminar todo")
}
