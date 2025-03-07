package com.example.tooldatabase

import android.app.Application
import androidx.navigation.NavHostController
import com.example.tooldatabase.data.ToolBodyRepository
import com.example.tooldatabase.data.ToolDatabase

class ToolDatabaseApplication : Application() {
    val database by lazy {
        ToolDatabase.createDatabase(this)
    }

    val toolBodyRepository: ToolBodyRepository by lazy {
        ToolBodyRepository(database.toolBodyDao)
    }

    lateinit var navController: NavHostController

    companion object {
        lateinit var context: ToolDatabaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}