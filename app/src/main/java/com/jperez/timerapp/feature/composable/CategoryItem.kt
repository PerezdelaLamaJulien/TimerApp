package com.jperez.timerapp.feature.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.CategoryUI
import com.jperez.timerapp.ui.theme.TimerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    categoryUI: CategoryUI,
    modifier: Modifier = Modifier,
) {
    val color = Color(categoryUI.color.value)
    Card(
        modifier = modifier
            .widthIn(min = 80.dp, max= 180.dp)
            .height(100.dp)
            .padding(0.dp),
        border = BorderStroke(1.dp, color),

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Badge(
                containerColor = Color(categoryUI.color.value),
                contentColor = Color.White,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = categoryUI.categoryType.icon,
                    contentDescription = "Email"
                )
            }
            Text(
                text = categoryUI.name,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.StartEllipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun CategoryItemPreviewLight() {
    TimerAppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CategoryItem(
                categoryUI = CategoryUI(
                    name = "Lecture",
                    color = CategoryColor.DARK_GREEN,
                    categoryType = CategoryType.READ
                )
            )
        }
    }
}

@Composable
@Preview
fun CategoryItemPreviewDark() {
    TimerAppTheme(darkTheme = true) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CategoryItem(
                categoryUI = CategoryUI(
                    name = "Lecture",
                    color = CategoryColor.DARK_GREEN,
                    categoryType = CategoryType.READ
                )
            )
        }
    }
}