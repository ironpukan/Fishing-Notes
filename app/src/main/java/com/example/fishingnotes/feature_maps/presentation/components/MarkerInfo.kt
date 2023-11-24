package com.example.fishingnotes.feature_maps.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.unit.dp
import com.example.fishingnotes.feature_maps.presentation.MarkerTextFieldState
import com.example.fishingnotes.feature_notes.presentation.add_edit_note.components.TransparentHintTextField

@Composable
fun MarkerInfo(
    modifier: Modifier = Modifier,
    titleState: MarkerTextFieldState,
    savedMarker: Boolean,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    onAddMarkerClick: () -> Unit,
    onDeleteMarkerClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .clip(
                CutCornerShape(
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            )
    ) {
        if (!savedMarker) {
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = onValueChange,
                onFocusChange = onFocusChange,
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = onAddMarkerClick
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "Add marker"
                )
            }
        } else {
            Text(
                text = titleState.text,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = onDeleteMarkerClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete marker"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onAddNoteClick) {
                Text(text = "Add note")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
