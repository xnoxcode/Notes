package epm.xnox.notes.presentation.edition.ui

sealed class EditionEvent {
    data object SaveNote : EditionEvent()
    data object DeleteNote : EditionEvent()
    data class GetNote(val id: Int) : EditionEvent()
    data class SetMark(val mark: Boolean) : EditionEvent()
    data class SetTitle(val title: String) : EditionEvent()
    data class SetContent(val content: String) : EditionEvent()
}