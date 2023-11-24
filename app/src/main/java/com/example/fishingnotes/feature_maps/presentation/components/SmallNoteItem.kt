package com.example.fishingnotes.feature_maps.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fishingnotes.feature_notes.domain.model.Note
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun SmallNoteItem(
    modifier: Modifier = Modifier,
    note: Note
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = note.timestamp.format(
                DateTimeFormatter.ofLocalizedDateTime(
                    FormatStyle.MEDIUM
                )
            ),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
