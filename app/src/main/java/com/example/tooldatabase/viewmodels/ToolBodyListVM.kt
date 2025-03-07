package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.ToolBodyDao
import com.example.tooldatabase.data.ToolDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

@OptIn(ExperimentalCoroutinesApi::class)
class ToolBodyListVM(var toolBodyDao: ToolBodyDao) : ViewModel() {
    private val _stateFilterFlow = MutableStateFlow<Filter>(Filter())
    var stateFilterFlow = _stateFilterFlow.asStateFlow()

    private val _items = _stateFilterFlow
        .flatMapLatest { filter ->
            toolBodyDao.getToolBodyByTest(filter.nmlDiameter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items = _items

    fun updateFilter(filter: Filter) {
        _stateFilterFlow.update {
            println("ToolBodyListVM updateFilter $filter")
            filter
        }
    }
}

class ToolBodyListVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        val database: ToolDatabase = (extras[APPLICATION_KEY] as ToolDatabaseApplication).database
        return ToolBodyListVM(toolBodyDao = database.toolBodyDao) as T
    }
}

data class Filter(
    var nmlDiameter: Int? = 125,
)

data class Control(
    var value: Int,
    var min: Int,
    var max: Int,
)
