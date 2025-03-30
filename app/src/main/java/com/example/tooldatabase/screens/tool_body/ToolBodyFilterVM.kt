package com.example.tooldatabase.screens.tool_body

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.ToolBodyRepository
import com.example.tooldatabase.models.filter.FieldFilter
import com.example.tooldatabase.models.filter.FilterToolBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@OptIn(ExperimentalCoroutinesApi::class)
class ToolBodyListVM(var repository: ToolBodyRepository) : ViewModel() {
    private var _stateFilterToolBodyFlow = MutableStateFlow(FilterToolBody())
    val stateFilterFlow = _stateFilterToolBodyFlow.asStateFlow()

    private val _items = _stateFilterToolBodyFlow
        .flatMapLatest { filter ->
            repository.getToolBodyListFlow(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items = _items

    fun updateFilter(fieldFilter: FieldFilter) {
        _stateFilterToolBodyFlow.update {
            it.fields
                .put(
                    fieldFilter.filedName.name,
                    (fieldFilter).copy()
                )

            it.copy()
        }
    }

    private fun updateValues() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFilterToolBodyFlow.update { filter ->
                repository.updateValues(filter)
            }
        }
    }

    fun updateAvailableValues() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFilterToolBodyFlow.update { filter ->
                repository.updateAvailableValues(filter)
            }
        }
    }

    init {
        updateValues()
        updateAvailableValues()
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
