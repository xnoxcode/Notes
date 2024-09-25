package epm.xnox.notes.presentation.edition.ui

sealed class EditionMenu(val name: String) {
    data object DELETE : EditionMenu("Eliminar")
}