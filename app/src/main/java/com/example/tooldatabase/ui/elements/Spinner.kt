package com.example.tooldatabase.ui.elements

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tooldatabase.ui.theme.ThemeColor

data class SpinnerOption<T>(
    val title: String,
    val value: T,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Spinner(
    label: String = "spinner.label",
    options: List<SpinnerOption<T>>,
    onClickItem: ((value: T?) -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {},
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            label = {
                Text(
                    text = label,
                )
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .onFocusChanged {
                    expanded = it.isFocused
                },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
            modifier = Modifier.requiredSizeIn(maxHeight = 328.dp),
        ) {
            options.forEach { option ->
                val optionText = option.title

                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = optionText,
                            fontSize = 16.sp
                        )
                   },
                    onClick = {
                        expanded = false

                        selectedText = if (option.value != null) option.title else ""

                        if (onClickItem != null) {
                            onClickItem(option.value)
                        }

                        focusManager.clearFocus()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview
@Composable
fun SpinnerPreview() {
    Spinner(
        label = "SpinnerPreview",
        options = listOf(
            SpinnerOption("Пусто", null),
            SpinnerOption("8 мм", 8),
            SpinnerOption("12 мм", 12),
            SpinnerOption("16 мм", 16),
            SpinnerOption("25 мм", 25),
            SpinnerOption("32 мм", 32),
            SpinnerOption("40 мм", 40),
            SpinnerOption("50 мм", 50),
            SpinnerOption("63 мм", 63),
        )
    )
}