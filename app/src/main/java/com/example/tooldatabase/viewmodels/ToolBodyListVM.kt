package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.ToolBodyDao
import com.example.tooldatabase.data.ToolDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlin.reflect.KClass

class ToolBodyListVM(toolBodyDao: ToolBodyDao) : ViewModel() {
    val toolBodyList = toolBodyDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

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