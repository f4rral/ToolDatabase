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

    @Query("SELECT * FROM tool_body WHERE nmlDiameter = :nmlDiameter")
    fun getToolBodyByDiameter(nmlDiameter: Int?): Flow<List<ToolBody>>

    @Update
    suspend fun update(item: ToolBody)

    @RawQuery(observedEntities = [ToolBody::class])
    fun rawQuery(query: RoomRawQuery): Flow<List<ToolBody>>
}