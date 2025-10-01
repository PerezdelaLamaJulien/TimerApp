package com.jperez.timerapp.feature.composable

import android.annotation.SuppressLint
import android.text.format.DateUtils
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.CategoryUI
import com.jperez.timerapp.feature.model.MainScreenUI
import com.jperez.timerapp.ui.theme.TimerAppTheme
import com.jperez.timerapp.ui.theme.Typography
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainScreenUI,
    duration: Duration,
    isPaused: Boolean,
    onSettingsTap: () -> Unit,
    onSelectedCategoryChanged: (CategoryUI) -> Unit,
    onStart: () -> Unit,
    onPaused: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Timer") },
                actions = {
                    IconButton(
                        onClick = onSettingsTap,
                    ) {
                        Icon(imageVector = Lucide.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .align(Alignment.CenterHorizontally)
                    .border(width = 0.5.dp, color = Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = DateUtils.formatElapsedTime(duration.seconds),
                    fontSize = 42.sp,
                    style = Typography.titleLarge
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var categoryExpanded by remember { mutableStateOf(false) }
                if (uiState.selectedCategory != null) {
                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = { categoryExpanded = it }) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth(0.45f)
                        ) {
                            BoxWithConstraints{
                                val boxWithConstraintsScope = this
                                Text(
                                    text = uiState.selectedCategory.name,
                                    Modifier.width(boxWithConstraintsScope.maxWidth - 36.dp),
                                    textAlign = TextAlign.End,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
                            Box(Modifier.width(4.dp))
                            Badge(
                                containerColor = Color(uiState.selectedCategory.color.value),
                                contentColor = Color.White,
                                modifier = Modifier
                                    .size(32.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        categoryExpanded = true
                                    }
                            ) {
                                Icon(
                                    imageVector = uiState.selectedCategory.categoryType.icon,
                                    contentDescription = "Email"
                                )
                            }
                        }
                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }) {
                            uiState.categories.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Badge(
                                                containerColor = Color(option.color.value),
                                                contentColor = Color.White,
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = option.categoryType.icon,
                                                    contentDescription = "Email"
                                                )
                                            }
                                            Text(option.name)
                                        }
                                    },
                                    onClick = {
                                        onSelectedCategoryChanged.invoke(option)
                                        categoryExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }

                Box(Modifier.width(12.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    if (duration.isZero)
                        Button(
                            onClick = onStart,
                        ) {
                            Text("Start Timer")
                        }

                    if (!duration.isZero)
                        Column {
                            Button(
                                onClick = onPaused,
                            ) {
                                Text(if (isPaused) "Resume Timer" else "Pause Timer")
                            }
                            Box(modifier = Modifier.width(12.dp))
                            Button(
                                onClick = onStop,
                            ) {
                                Text("Stop Timer")
                            }
                        }
                }
            }
            LazyColumn {
                items(uiState.entries.size) { index ->
                    EntryListItem(
                        entry = uiState.entries[index],
                        onCardClick = {}
                    )
                }
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun MainScreenPreviewLight() {
    TimerAppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            MainScreen(
                uiState = MainScreenUI(
                    selectedCategory = CategoryUI(
                        id = "uid",
                        name = "Category",
                        categoryType = CategoryType.DEFAULT,
                        color = CategoryColor.DARK_GREEN
                    )
                ),
                duration = Duration.ZERO,
                isPaused = false,
                onSettingsTap = {},
                onSelectedCategoryChanged = {},
                onStart = {},
                onPaused = {},
                onStop = {},
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun MainScreenPreviewDark() {
    TimerAppTheme(darkTheme = true) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            MainScreen(
                uiState = MainScreenUI(
                    selectedCategory = CategoryUI(
                        id = "uid",
                        name = "Ceci est un tres long nom de category qu'on voudrait affiché",
                        categoryType = CategoryType.DEFAULT,
                        color = CategoryColor.DARK_GREEN
                    )
                ),
                duration = Duration.ZERO,
                isPaused = false,
                onSettingsTap = {},
                onStart = {},
                onPaused = {},
                onStop = {},
                onSelectedCategoryChanged = {},
            )
        }
    }
}