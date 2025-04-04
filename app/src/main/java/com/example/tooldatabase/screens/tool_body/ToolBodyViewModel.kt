package com.example.tooldatabase.screens.tool_body

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.db.tool_body.ToolBody
import com.example.tooldatabase.repository.ToolBodyRepository
import com.example.tooldatabase.models.filter.FieldFilter
import com.example.tooldatabase.models.filter.FilterToolBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


class ToolBodyListVM(var repository: ToolBodyRepository) : ViewModel() {
    private var _stateFilter = MutableStateFlow(FilterToolBody())
    val stateFilter = _stateFilter.asStateFlow()

    private val _stateList: MutableStateFlow<List<ToolBody>> = MutableStateFlow(listOf())
    var stateList = _stateList.asStateFlow()

    private fun updateValues() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFilter.update { filter ->
                repository.updateValues(filter)
            }
        }
    }

    private fun updateAvailableValues() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFilter.update { filter ->
                repository.updateAvailableValues(filter)
            }
        }
    }

    private fun updateList(filter: FilterToolBody) {
        CoroutineScope(Dispatchers.IO).launch {
            _stateList.update {
                repository.getToolBodyList(filter)
            }
        }
    }

    fun updateFilter(fieldFilter: FieldFilter) {
        _stateFilter.update { filter ->
            filter.fields[fieldFilter.filedName.name] = (fieldFilter).copy()
            filter.copy()
        }
    }

    fun update() {
        updateAvailableValues()
        updateList(_stateFilter.value)
    }

    init {
        updateValues()
        updateAvailableValues()
        updateList(_stateFilter.value)
    }
}

class ToolBodyListVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        val repository: ToolBodyRepository = (extras[APPLICATION_KEY] as ToolDatabaseApplication).toolBodyRepository
        return ToolBodyListVM(repository = repository) as T
    }
}
