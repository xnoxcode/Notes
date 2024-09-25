package epm.xnox.notes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    enable: Boolean = false,
    singleLine: Boolean = true,
    onTextChange: (String) -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        enabled = enable,
        value = value,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        singleLine = singleLine,
        onValueChange = { newText ->
            onTextChange(newText)
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = label,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                innerTextField()
            }
        }
    )
}