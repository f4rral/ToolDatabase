package com.example.tooldatabase.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.ui.components.tool_body.ToolBodyList
import com.example.tooldatabase.ui.layouts.ScreenLayout
import com.example.tooldatabase.data.ToolBody
import com.example.tooldatabase.ui.elements.ButtonText
import com.example.tooldatabase.viewmodels.ToolBodyListVM
import com.example.tooldatabase.viewmodels.ToolBodyListVMFactory

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
            TextField(
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