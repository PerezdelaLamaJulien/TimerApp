package com.jperez.timerapp.feature.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.CategoryUI
import com.jperez.timerapp.ui.theme.TimerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryModalBottomSheet(
    onDismissed: () -> Unit,
    onSave: (CategoryUI) -> Unit,
    sheetState: SheetState,
    categoryUI: CategoryUI? = null,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissed,
        sheetState = sheetState
    ) {
        CategoryModalBottomSheetContent(
            onSave = onSave,
            modifier = Modifier.fillMaxSize(),
            categoryUI = categoryUI
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryModalBottomSheetContent(
    onSave: (CategoryUI) -> Unit,
    modifier: Modifier = Modifier,
    categoryUI: CategoryUI? = null,
) {
    val nameTextFieldState = rememberTextFieldState(initialText = categoryUI?.name ?: "Name")

    val colorTextFieldState = rememberTextFieldState(initialText = categoryUI?.color?.name ?: CategoryColor.DARK_GREEN.name)
    val colorOptions: List<String> = CategoryColor.entries.map { it.name }
    var colorExpanded by remember { mutableStateOf(false) }

    val typeTextFieldState = rememberTextFieldState(initialText = categoryUI?.categoryType?.name ?: CategoryType.DEFAULT.name)
    val typeOptions: List<String> = CategoryType.entries.map { it.name }
    var typeExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryItem(
                categoryUI = CategoryUI(
                    nameTextFieldState.text.toString(),
                    CategoryColor.valueOf(colorTextFieldState.text.toString()),
                    CategoryType.valueOf(typeTextFieldState.text.toString())
                )
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                state = nameTextFieldState,
                label = { Text("Category name") }
            )

            ExposedDropdownMenuBox(expanded = colorExpanded, onExpandedChange = { colorExpanded = it }) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    state = colorTextFieldState,
                    readOnly = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    label = { Text("Category Color") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = colorExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(expanded = colorExpanded, onDismissRequest = { colorExpanded = false }) {
                    colorOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                            onClick = {
                                colorTextFieldState.setTextAndPlaceCursorAtEnd(option)
                                colorExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(expanded = typeExpanded, onExpandedChange = { typeExpanded = it }) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    state = typeTextFieldState,
                    readOnly = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    label = { Text("Category Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(expanded = typeExpanded, onDismissRequest = { typeExpanded = false }) {
                    typeOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                            onClick = {
                                typeTextFieldState.setTextAndPlaceCursorAtEnd(option)
                                typeExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            // Sheet content
            Button(onClick = {
                onSave(CategoryUI(
                    nameTextFieldState.text.toString(),
                    CategoryColor.valueOf(colorTextFieldState.text.toString()),
                    CategoryType.valueOf(typeTextFieldState.text.toString())
                ))
            }) {
                Text("Save")
            }
    }
}

@Composable
@Preview
fun CategoryModalBottomSheetPreviewLight(){
    TimerAppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CategoryModalBottomSheetContent(
                onSave = {},
                categoryUI = null
            )
        }
    }
}

@Composable
@Preview
fun CategoryModalBottomSheetPreviewDark(){
    TimerAppTheme(darkTheme = true) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            CategoryModalBottomSheetContent(
                onSave = {},
                categoryUI = null
            )
        }
    }
}