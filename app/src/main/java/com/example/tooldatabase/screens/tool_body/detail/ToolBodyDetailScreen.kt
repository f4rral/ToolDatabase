package com.example.tooldatabase.screens.tool_body.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.navigation.NavigationRoute
import com.example.tooldatabase.ui.components.tool_body.ToolBodyDetail
import com.example.tooldatabase.ui.layouts.ScreenLayout

@Composable
fun ToolBodyDetailScreen(toolBodyId: Int) {
    val vmToolBodyDetail: ToolBodyDetailVM = viewModel(
        factory = ToolBodyDetailVMFactory(toolBodyId = toolBodyId)
    )

    val toolBody = vmToolBodyDetail.toolBody.collectAsState()

    ScreenLayout(
        title = "",
        onNavBack = {
            ToolDatabaseApplication.context.navController.navigate(
                route = NavigationRoute.TOOL_BODY_LIST
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ToolBodyDetail(toolBody = toolBody.value)
        }
    }
}