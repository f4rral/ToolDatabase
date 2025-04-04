package com.example.tooldatabase.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SpinnerOption<T>(
    val title: String,
    val value: T,
    val isEnabled: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Spinner(
    label: String = "spinner.label",
    currentValue: T? = null,
    options: List<SpinnerOption<T>> = listOf(),
    onClickItem: ((value: T?) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(currentValue?.toString() ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selected,
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
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .requiredSizeIn(maxHeight = 328.dp)
        ) {
            options.forEach { option ->
                val optionText = option.title

                DropdownMenuItem(
                    enabled = option.isEnabled,
                    text = {
                        Text(
                            text = optionText,
                            fontSize = 16.sp
                        )
                   },
                    onClick = {
                        expanded = false
                        selected = if (option.value != null) option.title else ""

                        if (onClickItem != null) {
                            onClickItem(option.value)
                        }

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