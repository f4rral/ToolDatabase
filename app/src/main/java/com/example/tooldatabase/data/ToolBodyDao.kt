package com.example.tooldatabase.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolBodyDao {
    @Query("SELECT * FROM tool_body ORDER BY id ASC")
    fun getAll(): Flow<List<ToolBody>>

    @Query("SELECT * FROM tool_body WHERE nmlDiameter > :nmlDiameter AND ZEFP=5")
    fun getToolBodyByTest(nmlDiameter: Int): Flow<List<ToolBody>>
}