package com.jperez.timerapp.feature.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.CirclePlus
import com.composables.icons.lucide.Lucide
import com.jperez.timerapp.feature.viewmodel.SettingsViewModel
import com.jperez.timerapp.ui.theme.TimerAppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val categoryItems = viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Lucide.ArrowLeft,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (showBottomSheet) {
            CategoryModalBottomSheet(
                onDismissed = {
                    showBottomSheet = false
                },
                onSave = { categoryUI ->
                    viewModel.saveCategory(categoryUI)
                    showBottomSheet = false
                },
                sheetState = sheetState,
                categoryUI = null
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            // todo : clean database
            // todo : show personal category name in time list
            // todo : default minimum time to add in database
            // todo : show credits
            // todo : buy me a coffee link

            Row(
                horizontalArrangement = Arrangement.Center

            ) {
                Text("Categories", modifier = Modifier.align(Alignment.CenterVertically))
                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        imageVector = Lucide.CirclePlus,
                        contentDescription = "Back button"
                    )
                }
            }

            FlowRow (modifier = Modifier
                .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                categoryItems.value.forEach { categoryUI ->
                    CategoryItem(
                        categoryUI,
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun SettingsScreenPreviewLight() {
    TimerAppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            SettingsScreen(
                navigateBack = {},
                viewModel = SettingsViewModel()
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun SettingsScreenPreviewDark() {
    TimerAppTheme(darkTheme = true) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            SettingsScreen(
                navigateBack = {},
                viewModel = SettingsViewModel()
            )
        }
    }
}