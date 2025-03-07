package com.example.tooldatabase.data

import kotlinx.coroutines.flow.Flow

class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    fun getAll(): Flow<List<ToolBody>> {
        return toolBodyDao.getAll()
    }

    fun getToolBodyByDiameter(nmlDiameter: Int?): Flow<List<ToolBody>> {
        return if (nmlDiameter != null) {
            toolBodyDao.getToolBodyByDiameter(nmlDiameter)
        } else {
            toolBodyDao.getAll()
        }

    }

    suspend fun update(toolBody: ToolBody) {
        toolBodyDao.update(toolBody)
    }
}