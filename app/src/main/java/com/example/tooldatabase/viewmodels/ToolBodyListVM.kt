package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.AvailableFilters
import com.example.tooldatabase.data.ControlFilter2
import com.example.tooldatabase.data.Filter
import com.example.tooldatabase.data.Filter2
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
    private val _stateFilterFlow = MutableStateFlow(Filter())
    var stateFilterFlow = _stateFilterFlow.asStateFlow()

    private val _availableFilters = _stateFilterFlow
        .flatMapLatest { filter ->
            repository.getAvailableFilters(filter)
        }
        .stateIn(
            scope = viewModelScope,
            started =  SharingStarted.WhileSubscribed(),
            initialValue = AvailableFilters()
        )
    var availableFilters = _availableFilters

    private val _items = _stateFilterFlow
        .flatMapLatest { filter ->
            repository.getToolBodyList(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items = _items

    fun updateFilter(filter: Filter) {
        _stateFilterFlow.update {
            filter
        }
    }

    private var _stateFilter2Flow = MutableStateFlow(Filter2())
    val stateFilter2Flow = _stateFilter2Flow.asStateFlow()

    fun update() {
        println("ToolDataBaseApp update")

        CoroutineScope(Dispatchers.IO).launch {
            _stateFilter2Flow.update { filter2 ->
//                val list = repository.getUpdateAvailableValues2(
//                    filter = filter2,
//                    fieldName = NameField.ZEFP
//                )

//                println("ToolDataBaseApp F $list")

                repository.getAllUpdateAvailableValues2(filter2)

//                filter2
            }
        }
    }

    private val _items2 = _stateFilter2Flow
        .flatMapLatest { filter ->
            println("ToolDataBaseApp S $filter")
            repository.getToolBodyList(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items2 = _items2

    fun <T> updateFilter2(filter: Filter2, fieldName: NameField, value: Any) {
        filter.fields[fieldName.name] = filter.fields[fieldName.name]!!.copy()
//        println("ToolDataBaseApp M ${filter.fields[fieldName.name]}")

        var t = filter.fields
        t.put(fieldName.name, (filter.fields[fieldName.name] as ControlFilter2<Any>).copy(currentValue = value as T))

        _stateFilter2Flow.update {
            filter.copy()
        }
    }

    private fun updateValues() {
        println("ToolDataBaseApp updateValues")

        CoroutineScope(Dispatchers.IO).launch {
            _stateFilter2Flow.update { filter2 ->
//                println("ToolDataBaseApp G ${repository.getUpdateValues2(filter2)}")
                repository.getUpdateValues2(filter2)
            }
        }
    }

    init {
        updateValues()
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
