package com.example.tooldatabase.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.ui.components.tool_body.ToolBodyList
import com.example.tooldatabase.ui.layouts.ScreenLayout
import com.example.tooldatabase.data.ToolBody
import com.example.tooldatabase.ui.elements.ButtonText
import com.example.tooldatabase.ui.elements.Spinner
import com.example.tooldatabase.ui.elements.SpinnerOption
import com.example.tooldatabase.viewmodels.ToolBodyListVM
import com.example.tooldatabase.viewmodels.ToolBodyListVMFactory


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen() {
    val vmToolBodyList: ToolBodyListVM = viewModel(
        factory = ToolBodyListVMFactory()
    )

    val items = vmToolBodyList.items.collectAsState()
    val stateFilter = vmToolBodyList.stateFilterFlow.collectAsState()
    var sliderPosition = mutableFloatStateOf(0f)

    ScreenLayout(
        title = "Home",
    ) {

        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxSize()
        ) {
//            Slider(
//                value = sliderPosition.floatValue,
//                valueRange = 8f..1000f,
//                onValueChange = { sliderPosition.floatValue = it }
//            )
//
//            Slider(
//                value = sliderPosition.floatValue,
//                onValueChange = { sliderPosition.floatValue = it },
//                steps = 10,
//                valueRange = 8f..1000f
//            )
//
//            Slider(
//                value = sliderPosition.floatValue,
//                onValueChange = { sliderPosition.floatValue = it },
//                steps = 10,
//                valueRange = 8f..1000f
//            )
//
//            Text(text = sliderPosition.floatValue.toString())

            Spinner(
                label = "Номинальный диаметр",
                options = listOf(
                    SpinnerOption("Любой", null),
                    SpinnerOption("8 мм", 8),
                    SpinnerOption("12 мм", 12),
                    SpinnerOption("16 мм", 16),
                    SpinnerOption("25 мм", 25),
                    SpinnerOption("32 мм", 32),
                    SpinnerOption("40 мм", 40),
                    SpinnerOption("50 мм", 50),
//                    SpinnerOption("50.8 мм", 50.8),
                    SpinnerOption("63 мм", 63),
                    SpinnerOption("80 мм", 80),
                    SpinnerOption("100 мм", 100),
                    SpinnerOption("125 мм", 125),
                    SpinnerOption("160 мм", 160),
                    SpinnerOption("200 мм", 200),
                    SpinnerOption("315 мм", 315),
                ),
                onClickItem = { value: Int? ->
                    vmToolBodyList.updateFilter(
                        filter = stateFilter.value.copy(nmlDiameter = value)
                    )
                }
            )

            OutlinedTextField(
                label = { Text(text = "Номинальный диаметр, мм")},
                modifier = Modifier.fillMaxWidth(),
                value = if (stateFilter.value.nmlDiameter != null) stateFilter.value.nmlDiameter.toString() else "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    println("onValueChange $it")
                    val value = if (it.toIntOrNull() != null) it.toInt()  else null

                    vmToolBodyList.updateFilter(
                        filter = stateFilter.value.copy(nmlDiameter = value)
                    )
                }
            )

            ButtonText(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                text = "Update",
                onClick = {
                    vmToolBodyList.updateFilter(
                        filter = stateFilter.value.copy(nmlDiameter = 80)
                    )
                }
            )

            HomeBody(
                toolBodyList = items.value
            )
        }

    }
}

@Composable
fun HomeBody(
    toolBodyList: List<ToolBody>,
) {
    Column(
        modifier = Modifier
//            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
    ) {
        ToolBodyList(
            toolBodyList = toolBodyList
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ScreenLayout {
        HomeBody(
            toolBodyList = com.example.tooldatabase.ui.components.tool_body.ToolBody.toolBodyListFake,
        )
    }
}
