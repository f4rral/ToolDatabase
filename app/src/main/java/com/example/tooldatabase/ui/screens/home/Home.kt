package com.example.tooldatabase.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.ui.components.tool_body.ToolBodyList
import com.example.tooldatabase.ui.layouts.ScreenLayout
import com.example.tooldatabase.data.ToolBody
import com.example.tooldatabase.viewmodels.ToolBodyListVM
import com.example.tooldatabase.viewmodels.ToolBodyListVMFactory

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen() {
    val vmToolBodyList: ToolBodyListVM = viewModel(
        factory = ToolBodyListVMFactory()
    )
    val toolBodyList = vmToolBodyList.toolBodyList.collectAsState()

    ScreenLayout(
        title = "Home",
    ) {
        HomeBody(
            toolBodyList = toolBodyList.value,
        )
    }
}

@Composable
fun HomeBody(
    toolBodyList: List<ToolBody>,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
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