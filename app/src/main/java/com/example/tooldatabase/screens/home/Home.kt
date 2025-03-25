package com.example.tooldatabase.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.data.NameField
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
                label = "Серия",
                options = (listOf(null) + stateFilter.value.fields[NameField.SERIES.name]!!.values).map {
                    if (it == null) {
                        SpinnerOption("Любая", null)
                    } else {
                        SpinnerOption("$it", it)
                    }
                },
                onClickItem = { value ->
                    vmToolBodyList.updateFilter<String>(
                        filter = stateFilter.value,
                        fieldName = NameField.SERIES,
                        value = value!!
                    )
                }
            )

            Spinner(
                label = "Номинальный диаметр",
                options = (listOf(null) + stateFilter.value.fields[NameField.NML_DIAMETER.name]!!.values).map {
                    if (it == null) {
                        SpinnerOption("Любой", null)
                    } else {
                        SpinnerOption(
                            title = "$it мм",
                            value = it,
                            isEnabled = it in stateFilter.value.fields[NameField.NML_DIAMETER.name]!!.availableValues
                        )
                    }
                },
                onClickItem = { value ->
                    vmToolBodyList.updateFilter<Double>(
                        filter = stateFilter.value,
                        fieldName = NameField.NML_DIAMETER,
                        value = value!!
                    )
                }
            )

            Spinner(
                label = "Кол-во зубьев",
                options = (listOf(null) + stateFilter.value.fields[NameField.ZEFP.name]!!.values).map {
                    if (it == null) {
                        SpinnerOption("Любой", null)
                    } else {
                        SpinnerOption(
                            title = "$it",
                            value = it,
                            isEnabled = it in stateFilter.value.fields[NameField.ZEFP.name]!!.availableValues
                        )
                    }
                },
                onClickItem = { value ->
                    vmToolBodyList.updateFilter<Int>(
                        filter = stateFilter.value,
                        fieldName = NameField.ZEFP,
                        value = value!!
                    )
                }
            )

//            OutlinedTextField(
//                label = { Text(text = "Номинальный диаметр, мм")},
//                modifier = Modifier.fillMaxWidth(),
//                value = if (stateFilter.value.nmlDiameter != null) stateFilter.value.nmlDiameter.toString() else "",
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                onValueChange = {
//                    println("onValueChange $it")
//                    val value = if (it.toDoubleOrNull() != null) it.toDouble()  else null
//
//                    vmToolBodyList.updateFilter(
//                        filter = stateFilter.value.copy(nmlDiameter = value)
//                    )
//                }
//            )

            ButtonText(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                text = "Test",
                onClick = {
                    vmToolBodyList.update()
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
        if (toolBodyList.isNotEmpty()) {
            ToolBodyList(
                toolBodyList = toolBodyList
            )
        } else {
            Text(
                text = "Не найдено"
            )
        }
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
