package com.jperez.timerapp.feature.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.EntryUI
import com.jperez.timerapp.ui.theme.TimerAppTheme

@Composable
fun EntryListItem(
    onCardClick: () -> Unit,
    entry: EntryUI,
    modifier: Modifier = Modifier,
) {
    val color = Color(entry.color.value)
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onCardClick()
            },
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        border = BorderStroke(1.dp, color),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Badge(
                containerColor = color,
                contentColor = Color.White,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = entry.categoryType.icon,
                    contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 0.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = entry.duration, style = typography.titleMedium)
                Text(text = entry.date, style = typography.bodyMedium)
                Text(text = entry.description, style = typography.bodyMedium.copy(fontStyle = FontStyle.Italic) )
            }
        }
    }
}

@Composable
@Preview
fun EntryListItemPreviewLight(){
    TimerAppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        EntryListItem(
            onCardClick = {},
            entry = EntryUI(
                date = "12 Janvier 2025",
                duration = "1 minutes 12 secondes",
                description = "Lire Dune",
                color = CategoryColor.DARK_GREEN,
                categoryType = CategoryType.DEFAULT,
            )
        )
        }
    }
}

@Composable
@Preview
fun EntryListItemPreviewDark(){
    TimerAppTheme(darkTheme = true) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            EntryListItem(
                onCardClick = {},
                entry = EntryUI(
                    date = "12 Janvier 2025",
                    duration = "1 minutes 12 secondes",
                    description = "Lire Dune",
                    color = CategoryColor.DARK_GREEN,
                    categoryType = CategoryType.DEFAULT,
                )
            )
        }
    }
}