package epm.xnox.notes.presentation.edition.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import epm.xnox.notes.R
import epm.xnox.notes.domain.model.Note
import epm.xnox.notes.presentation.edition.viewModel.EditionViewModel
import epm.xnox.notes.ui.components.DialogAlert
import epm.xnox.notes.ui.components.PopupMenu
import epm.xnox.notes.ui.components.TextField

@Composable
fun ScreenEdition(
    backStackEntry: NavBackStackEntry,
    viewModel: EditionViewModel,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.value
    var editable: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val id = backStackEntry.arguments?.getInt("id")
        if (id == 0) editable = true
        else viewModel.onEvent(EditionEvent.GetNote(id!!))
    }

    Scaffold(
        floatingActionButton = {
            Footer(
                viewModel = viewModel,
                editable = editable,
                onNavigateBack
            ) { editable = true }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Header(viewModel, state, editable, onNavigateBack)
            Spacer(modifier = Modifier.height(15.dp))
            Body(viewModel, state, editable)
        }
    }
}

@Composable
fun Header(
    viewModel: EditionViewModel,
    state: Note,
    editable: Boolean,
    onNavigateBack: () -> Unit
) {
    val focusRequester = FocusRequester()
    var showDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val menuOptions = mapOf(EditionMenu.DELETE.name to Icons.Outlined.Delete)

    LaunchedEffect(editable) { focusRequester.requestFocus() }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onNavigateBack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
        TextField(
            enable = editable,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            value = state.title,
            label = stringResource(id = R.string.screen_edition_title_edition)
        ) { text ->
            viewModel.onEvent(EditionEvent.SetTitle(text))
        }
        if (editable) {
            IconButton(onClick = { viewModel.onEvent(EditionEvent.SetMark(!state.mark)) }) {
                Icon(
                    painter = painterResource(
                        id = if (state.mark) R.drawable.bookmark
                        else R.drawable.bookmark_outline
                    ),
                    contentDescription = null
                )
            }
        } else {
            IconButton(
                onClick = { showMenu = true },
                enabled = state.id != 0
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                PopupMenu(
                    expanded = showMenu,
                    options = menuOptions,
                    onDismiss = { showMenu = false },
                    onItemClick = { option ->
                        when (option) {
                            EditionMenu.DELETE.name -> showDialog = true
                        }
                    }
                )
            }
        }
        DialogAlert(
            show = showDialog,
            title = stringResource(id = R.string.dialog_delete_note_title),
            content = stringResource(id = R.string.dialog_delete_note_description),
            confirmButtonText = stringResource(id = R.string.dialog_btn_delete),
            onDismissButton = { showDialog = false },
            onDismissRequest = { showDialog = false },
            onConfirmButton = {
                viewModel.onEvent(EditionEvent.DeleteNote)
                showDialog = false
                onNavigateBack()
            }
        )
    }
}

@Composable
fun Body(viewModel: EditionViewModel, state: Note, editable: Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            enable = editable,
            singleLine = false,
            value = state.content,
            label = stringResource(id = R.string.screen_edition_content_edition)
        ) { text ->
            viewModel.onEvent(EditionEvent.SetContent(text))
        }
    }
}

@Composable
fun Footer(
    viewModel: EditionViewModel,
    editable: Boolean,
    onNavigateBack: () -> Unit,
    onEdit: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = {
            if (editable) {
                viewModel.onEvent(EditionEvent.SaveNote)
                onNavigateBack()
            } else {
                onEdit()
            }
        },
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(text = stringResource(id = if (editable) R.string.screen_edit_fab_save_note else R.string.screen_edit_fab_edit_note))
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector =
            if (editable) Icons.Default.Check
            else Icons.Default.Edit, contentDescription = null
        )
    }
}

