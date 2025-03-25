package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.ControlFilter
import com.example.tooldatabase.data.Filter
import com.example.tooldatabase.data.NameField
import com.example.tooldatabase.data.ToolBodyRepository
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
    private var _stateFilterFlow = MutableStateFlow(Filter())
    val stateFilterFlow = _stateFilterFlow.asStateFlow()

    private val _items = _stateFilterFlow
        .flatMapLatest { filter ->
            repository.getToolBodyList(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items = _items

    fun <T> updateFilter(filter: Filter, fieldName: NameField, value: Any) {
        filter.fields[fieldName.name] = filter.fields[fieldName.name]!!.copy()

        filter.fields
            .put(fieldName.name, (filter.fields[fieldName.name] as ControlFilter<Any>).copy(currentValue = value as T))

        _stateFilterFlow.update {
            filter.copy()
        }
    }

    private fun updateValues() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFilterFlow.update { filter ->
                repository.updateValues(filter)
            }
        }
    }

    fun updateAvailableValues() {
        CoroutineScope(Dispatchers.IO).launch {
            _stateFilterFlow.update { filter ->
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
