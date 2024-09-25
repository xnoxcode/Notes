package epm.xnox.notes.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PopupMenu(
    expanded: Boolean,
    options: Map<String, ImageVector>,
    onItemClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option.key) },
                leadingIcon = {
                    Icon(
                        imageVector = option.value,
                        contentDescription = null
                    )
                },
                onClick = {
                    onItemClick(option.key)
                    onDismiss()
                }
            )
        }
    }
}