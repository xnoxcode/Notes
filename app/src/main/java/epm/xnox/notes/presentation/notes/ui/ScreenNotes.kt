package epm.xnox.notes.presentation.notes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epm.xnox.notes.R
import epm.xnox.notes.domain.model.Note
import epm.xnox.notes.presentation.notes.viewModel.NotesViewModel
import epm.xnox.notes.ui.components.DialogAlert
import epm.xnox.notes.ui.components.PopupMenu

@Composable
fun ScreenNotes(
    viewModel: NotesViewModel,
    onNavigateToDetail: (id: Int) -> Unit,
    onNavigateToEdition: () -> Unit
) {
    val notes = viewModel.state.value

    Scaffold(
        floatingActionButton = { Footer(onNavigateToEdition) },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Header(notes, viewModel)
            Spacer(modifier = Modifier.height(10.dp))
            if (notes.isEmpty()) EmptyNotes()
            else Body(notes, onNavigateToDetail)
        }
    }
}

@Composable
fun Header(notes: List<Note>, viewModel: NotesViewModel) {
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val options = mapOf(NotesMenu.Delete.name to Icons.Outlined.Delete)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.app_name),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.font))
        )
        IconButton(
            enabled = notes.isNotEmpty(),
            onClick = { showMenu = true },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            PopupMenu(
                expanded = showMenu,
                options = options,
                onItemClick = {
                    showDialog = true
                }, onDismiss = {
                    showMenu = false
                }
            )
        }
        DialogAlert(
            show = showDialog,
            title = stringResource(id = R.string.dialog_delete_all_notes_title),
            content = stringResource(id = R.string.dialog_delete_all_notes_content),
            confirmButtonText = stringResource(id = R.string.dialog_btn_delete),
            onDismissButton = { showDialog = false },
            onDismissRequest = { showDialog = false },
            onConfirmButton = {
                viewModel.onEvent(NotesEvent.DeleteAllNotes)
                showDialog = false
            }
        )
    }
}

@Composable
fun Body(notes: List<Note>, onNavigateToDetail: (id: Int) -> Unit) {
    val lazyState = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        state = lazyState,
        columns = StaggeredGridCells.Fixed(2),
        content = {
            items(notes) { note ->
                NoteItem(note, onNavigateToDetail)
            }
        }
    )
}

@Composable
fun Footer(onNavigateToEdition: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onNavigateToEdition() },
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(text = stringResource(id = R.string.screen_notes_fab_notes_create))
        Spacer(modifier = Modifier.width(10.dp))
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
fun EmptyNotes() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.illustration_empty_notes),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.screen_notes_empty_notes_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.screen_notes_empty_notes_description),
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun NoteItem(note: Note, onNavigateToDetail: (id: Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 250.dp)
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onNavigateToDetail(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = note.content,
                modifier = Modifier.weight(1f, false),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (note.mark) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        text = note.date,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
            }
        }
    }
}