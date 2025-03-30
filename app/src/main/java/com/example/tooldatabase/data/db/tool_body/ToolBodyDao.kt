package com.example.tooldatabase.data.db.tool_body

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolBodyDao {
    @Update
    suspend fun updateToolBody(item: ToolBody)

    @Query("SELECT * FROM tool_body WHERE id = :id")
    fun getToolBodyById(id: Int): ToolBody

    @RawQuery(observedEntities = [ToolBody::class])
    fun getToolBodyList(query: RoomRawQuery): List<ToolBody>

    @RawQuery(observedEntities = [ToolBody::class])
    fun getListValuesInt(query: RoomRawQuery): List<Int>

    @RawQuery(observedEntities = [ToolBody::class])
    fun getListValuesDouble(query: RoomRawQuery): List<Double>

    @RawQuery(observedEntities = [ToolBody::class])
    fun getListString(query: RoomRawQuery): List<String>
}