package com.example.tooldatabase.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolBodyDao {
    @Query("SELECT * FROM tool_body ORDER BY id ASC")
    fun getAll(): Flow<List<ToolBody>>

//    @Query("SELECT * FROM tool_body WHERE nmlDiameter = :nmlDiameter")
//    fun getToolBodyByDiameter(nmlDiameter: Int?): Flow<List<ToolBody>>

    @Query("SELECT DISTINCT nmlDiameter FROM tool_body ORDER BY nmlDiameter ASC")
    fun getAllNmlDiameter(): Flow<List<Double>>

    @Query("SELECT DISTINCT ZEFP FROM tool_body ORDER BY ZEFP ASC")
    fun getAllZEFP(): Flow<List<Int>>

    @Query("SELECT DISTINCT series FROM tool_body ORDER BY series ASC")
    fun getAllSeries(): Flow<List<String>>

    @Query("""
        SELECT DISTINCT ZEFP FROM tool_body 
        WHERE nmlDiameter = 50 AND series = "MT1"
        ORDER BY ZEFP ASC
    """)
    fun getAvailableZEFP(): Flow<List<Int>>

    @Update
    suspend fun updateToolBody(item: ToolBody)

    @RawQuery(observedEntities = [ToolBody::class])
    fun getToolBodyList(query: RoomRawQuery): Flow<List<ToolBody>>

    @RawQuery(observedEntities = [ToolBody::class])
    fun rawQuery(query: RoomRawQuery): Flow<List<Int>>
}