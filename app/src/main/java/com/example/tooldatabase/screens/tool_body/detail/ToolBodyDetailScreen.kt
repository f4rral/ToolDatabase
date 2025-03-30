package com.example.tooldatabase.screens.tool_body.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.ui.components.tool_body.ToolBodyDetail
import com.example.tooldatabase.ui.layouts.ScreenLayout

@Composable
fun ToolBodyDetailScreen(toolBodyId: Int) {
    val vmToolBodyDetail: ToolBodyDetailVM = viewModel(
        factory = ToolBodyDetailVMFactory(toolBodyId = toolBodyId)
    )

    val toolBody = vmToolBodyDetail.toolBody.collectAsState()

    ScreenLayout(
        title = "ToolBodyDetail",
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .fillMaxSize()
        ) {
            ToolBodyDetail(toolBody = toolBody.value)
        }
    }
}