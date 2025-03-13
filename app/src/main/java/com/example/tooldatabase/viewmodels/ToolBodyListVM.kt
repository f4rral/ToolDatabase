package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.ToolBodyRepository
import com.example.tooldatabase.ui.elements.SpinnerOption
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

    private val _items = _stateFilterFlow
        .flatMapLatest { filter ->
            repository.filterQuery(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var items = _items

    private val _listNmlDiameter = MutableStateFlow<List<SpinnerOption<Double?>>>(listOf(
        SpinnerOption("Любой", null),
        SpinnerOption("8 мм", 8.0),
        SpinnerOption("12 мм", 12.0),
        SpinnerOption("16 мм", 16.0),
        SpinnerOption("25 мм", 25.0, false),
        SpinnerOption("32 мм", 32.0, false),
        SpinnerOption("40 мм", 40.0),
        SpinnerOption("50 мм", 50.0),
        SpinnerOption("50.8 мм", 50.8),
        SpinnerOption("63 мм", 63.0),
        SpinnerOption("80 мм", 80.0),
        SpinnerOption("100 мм", 100.0),
        SpinnerOption("125 мм", 125.0),
        SpinnerOption("160 мм", 160.0),
        SpinnerOption("200 мм", 200.0),
        SpinnerOption("315 мм", 315.0),
    ))
    var listNmlDiameter = _listNmlDiameter.asStateFlow()
//    var listFlow = repository.getUniqueNmlDiameterFlow()

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
    var nmlDiameter: Double? = 125.0,
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
