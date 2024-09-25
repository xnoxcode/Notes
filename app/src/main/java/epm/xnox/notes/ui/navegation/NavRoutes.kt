package epm.xnox.notes.ui.navegation

sealed class NavRoutes(val route: String) {

    data object ScreenNotes : NavRoutes("notes")

    data object ScreenEdition : NavRoutes("edition?={id}") {
        fun withArgument(id: Int) = "edition?=$id"
    }
}