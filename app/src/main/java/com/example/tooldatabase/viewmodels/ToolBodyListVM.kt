package com.example.tooldatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tooldatabase.ToolDatabaseApplication
import com.example.tooldatabase.data.ToolBodyDao
import com.example.tooldatabase.data.ToolDatabase
import kotlin.reflect.KClass

class ToolBodyListVM(contactDao: ToolBodyDao) : ViewModel() {
    val contactList = contactDao.getAll()
}

class ToolBodyListVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        val database: ToolDatabase = (extras[APPLICATION_KEY] as ToolDatabaseApplication).database

        return ToolBodyListVM(contactDao = database.toolBodyDao) as T
    }
}