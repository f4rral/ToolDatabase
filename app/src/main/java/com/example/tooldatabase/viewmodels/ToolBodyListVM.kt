package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.AvailableFilters
import com.example.tooldatabase.data.ToolBodyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

@OptIn(ExperimentalCoroutinesApi::class)
class ToolBodyListVM(var repository: ToolBodyRepository) : ViewModel() {
    private val _stateFilterFlow = MutableStateFlow(Filter())
    var stateFilterFlow = _stateFilterFlow.asStateFlow()

    private val _items = _stateFilterFlow
        .flatMapLatest { filter ->
            repository.filterQuery(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items = _items

    val availableFilters = repository.getAvailableFilters2()
        .stateIn(
            scope = viewModelScope,
            started =  SharingStarted.WhileSubscribed(),
            initialValue = AvailableFilters(
                allNmlDiameter = emptyList()
            )
        )

    fun updateFilter(filter: Filter) {
        _stateFilterFlow.update {
            println("ToolBodyListVM updateFilter $filter")
            filter
        }
    }

    fun update() {
        println("ToolBodyListVM update 1")
    }
}

class ToolBodyListVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        val repository: ToolBodyRepository = (extras[APPLICATION_KEY] as ToolDatabaseApplication).toolBodyRepository
        return ToolBodyListVM(repository = repository) as T
    }
}

data class Filter(
    var nmlDiameter: Double? = null,
    var ZEFP: Int? = null
)

data class Control(
    var value: Int,
    var min: Int,
    var max: Int,
)

data class SpinnerControl<T>(
    var value: String = "",
    var listValues: List<T>
)
