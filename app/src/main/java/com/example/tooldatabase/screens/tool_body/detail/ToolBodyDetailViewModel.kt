package com.example.tooldatabase.screens.tool_body.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.db.tool_body.ToolBody
import com.example.tooldatabase.repository.ToolBodyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ToolBodyDetailVM(
    val repository: ToolBodyRepository,
    val toolBodyId: Int
) : ViewModel() {
    private var _toolBody = MutableStateFlow(
        ToolBody(
            id = -1,
            title = "",
            series = "",
            kapr = 0.0,
            orderCode = "",
            nmlDiameter = 0.0,
            zefp = 0
        )
    )
    var toolBody = _toolBody.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _toolBody.update {
                repository.getToolBodyById(toolBodyId)
            }
        }
    }
}

class ToolBodyDetailVMFactory(
    private var toolBodyId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        val repository: ToolBodyRepository = (extras[APPLICATION_KEY] as ToolDatabaseApplication).toolBodyRepository
        return ToolBodyDetailVM(
            repository = repository,
            toolBodyId = toolBodyId
        ) as T
    }
}