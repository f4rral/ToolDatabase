package com.example.tooldatabase.screens.tool_body

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.ui.components.tool_body.ToolBodyList
import com.example.tooldatabase.ui.layouts.ScreenLayout
import com.example.tooldatabase.data.db.tool_body.ToolBody
import com.example.tooldatabase.data.db.tool_body.ToolBodyFakeData
import com.example.tooldatabase.models.filter.FieldFilter
import com.example.tooldatabase.models.filter.FilterToolBody
import com.example.tooldatabase.models.filter.NameField
import com.example.tooldatabase.navigation.NavigationRoute
import com.example.tooldatabase.ui.elements.ButtonText
import com.example.tooldatabase.ui.elements.Spinner
import com.example.tooldatabase.ui.elements.SpinnerOption
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun ToolBodyScreen() {
    val vmToolBodyList: ToolBodyListVM = viewModel(
        factory = ToolBodyListVMFactory()
    )

    val stateToolBodyList = vmToolBodyList.stateList.collectAsState()
    val stateFilter = vmToolBodyList.stateFilter.collectAsState()

    ScreenLayout(
        title = "ToolBodyList",
    ) {
        ToolBodyScreenUi(
            toolBodyFilterToolBody = stateFilter.value,
            onChangeFilter = { value, field ->
                println("ToolBodyApp F $value $field")

                vmToolBodyList.updateFilter(
                    fieldFilter = stateFilter.value.fields[field!!.filedName.name]!!.copy(currentValue = value)
                )

                vmToolBodyList.update()
            },
            toolBodyList = stateToolBodyList.value,
            onClickItem = { id ->
                println("ToolBodyApp L $id")
                ToolDatabaseApplication.context.navController.navigate(
                    route = "${NavigationRoute.TOOL_BODY_DETAIL}/$id"
                )
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBodyScreenUi(
    toolBodyFilterToolBody: FilterToolBody = FilterToolBody(),
    onChangeFilter: ((value: Any?, field: FieldFilter?) -> Unit)? = null,
    toolBodyList: List<ToolBody> = listOf(),
    onClickItem: ((id: Int) -> Unit)? = null
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(value = false)}

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
    ) {
        ButtonText(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            text = "Фильтр",
            onClick = {
                println("ToolBodyApp K")
                showBottomSheet = true
            }
        )

        if (toolBodyList.isNotEmpty()) {
            ToolBodyList(
                toolBodyList = toolBodyList,
                onClickItem = onClickItem
            )
        } else {
            Text(
                text = "Не найдено"
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    println("ToolBodyApp D")
                    showBottomSheet = false
                },
                shape = RoundedCornerShape(0.dp),
                sheetState = sheetState,
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.96f)
                        .padding(8.dp)
                ) {

                    ToolBodyFilter(
                        filterToolBody = toolBodyFilterToolBody,
                        onChangeFilter = onChangeFilter
                    )

                    ButtonText(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        text = "Показать",
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ToolBodyFilter(
    filterToolBody: FilterToolBody,
    onChangeFilter: ((value: Any?, field: FieldFilter?) -> Unit)? = null
) {
    println("Tool Body App $filterToolBody")

    Column {
        Spinner(
            label = "Серия",
            currentValue = filterToolBody.fields[NameField.SERIES.name]!!.currentValue,
            options = (listOf(null) + filterToolBody.fields[NameField.SERIES.name]!!.values).map {
                if (it == null) {
                    SpinnerOption("Любая", null)
                } else {
                    SpinnerOption(
                        title = "$it",
                        value = it,
                        isEnabled = it in filterToolBody.fields[NameField.SERIES.name]!!.availableValues
                    )
                }
            },
            onClickItem = { value ->
                    if (onChangeFilter != null) {
                        onChangeFilter(
                            value,
                            filterToolBody.fields[NameField.SERIES.name]
                        )
                    }
            }
        )

        Spinner(
            label = "Номинальный диаметр",
            currentValue = filterToolBody.fields[NameField.NML_DIAMETER.name]!!.currentValue,
            options = (listOf(null) + filterToolBody.fields[NameField.NML_DIAMETER.name]!!.values).map {
                if (it == null) {
                    SpinnerOption("Любой", null)
                } else {
                    SpinnerOption(
                        title = "$it мм",
                        value = it,
                        isEnabled = it in filterToolBody.fields[NameField.NML_DIAMETER.name]!!.availableValues
                    )
                }
            },
            onClickItem = { value ->
                if (onChangeFilter != null) {
                    onChangeFilter(
                        value,
                        filterToolBody.fields[NameField.NML_DIAMETER.name]
                    )
                }
            }
        )

        Spinner(
            label = "Кол-во зубьев",
            currentValue = filterToolBody.fields[NameField.ZEFP.name]!!.currentValue,
            options = (listOf(null) + filterToolBody.fields[NameField.ZEFP.name]!!.values).map {
                if (it == null) {
                    SpinnerOption("Любой", null)
                } else {
                    SpinnerOption(
                        title = "$it",
                        value = it,
                        isEnabled = it in filterToolBody.fields[NameField.ZEFP.name]!!.availableValues
                    )
                }
            },
            onClickItem = { value ->
                if (onChangeFilter != null) {
                    onChangeFilter(
                        value,
                        filterToolBody.fields[NameField.ZEFP.name]
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ScreenLayout {
        ToolBodyScreenUi(
            toolBodyFilterToolBody = FilterToolBody(),
            toolBodyList = ToolBodyFakeData.toolBodyListFake
        )
    }
}
