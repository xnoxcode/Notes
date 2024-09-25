package epm.xnox.notes.ui.navegation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import epm.xnox.notes.presentation.edition.ui.ScreenEdition
import epm.xnox.notes.presentation.edition.viewModel.EditionViewModel
import epm.xnox.notes.presentation.notes.ui.ScreenNotes
import epm.xnox.notes.presentation.notes.viewModel.NotesViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NavRoutes.ScreenNotes.route) {
            val notesViewModel = hiltViewModel<NotesViewModel>()
            ScreenNotes(
                notesViewModel,
                onNavigateToDetail = { id ->
                    navController.navigate(NavRoutes.ScreenEdition.withArgument(id))
                },
                onNavigateToEdition = {
                    navController.navigate(NavRoutes.ScreenEdition.route)
                }
            )
        }

        composable(
            route = NavRoutes.ScreenEdition.route,
            arguments = listOf(navArgument("id") { defaultValue = 0 })
        ) { backStackEntry ->
            val editionViewModel = hiltViewModel<EditionViewModel>()
            ScreenEdition(
                backStackEntry,
                editionViewModel, onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}