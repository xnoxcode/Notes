package epm.xnox.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import epm.xnox.notes.R

@Composable
fun DialogAlert(
    show: Boolean,
    title: String,
    content: String,
    confirmButtonText: String? = null,
    dismissButtonText: String? = null,
    onDismissButton: (() -> Unit)? = null,
    onConfirmButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = { onDismissRequest() },
            content = {
                Card(
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(28.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AlertDialogDefaults.containerColor)
                            .padding(16.dp)
                    ) {
                        val textStyleTitle = MaterialTheme.typography.headlineSmall
                        ProvideTextStyle(textStyleTitle) {
                            Box(Modifier.padding(16.dp))
                            {
                                Text(text = title)
                            }
                        }
                        val textStyleContent = MaterialTheme.typography.bodyLarge
                        ProvideTextStyle(textStyleContent) {
                            Box(Modifier.padding(start = 16.dp, end = 16.dp))
                            {
                                Text(text = content)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.align(Alignment.End))
                        {
                            CompositionLocalProvider(LocalContentColor provides AlertDialogDefaults.textContentColor) {
                                val textStyleButton =
                                    MaterialTheme.typography.labelMedium
                                ProvideTextStyle(value = textStyleButton, content = {
                                    if (onDismissButton != null) {
                                        TextButton(onClick = { onDismissButton() }) {
                                            ButtonDialog(
                                                text = if (dismissButtonText.isNullOrEmpty()) stringResource(
                                                    id = R.string.dialog_btn_cancel
                                                ) else dismissButtonText
                                            )
                                        }
                                    }
                                    TextButton(onClick = { onConfirmButton() }) {
                                        ButtonDialog(
                                            text = if (confirmButtonText.isNullOrEmpty()) stringResource(
                                                id = R.string.dialog_btn_ok
                                            ) else confirmButtonText
                                        )
                                    }
                                })
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ButtonDialog(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.onBackground)
}