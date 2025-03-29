package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import com.example.tooldatabase.data.db.tool_body.ToolBody
import com.example.tooldatabase.data.db.tool_body.ToolBodyDao
import com.example.tooldatabase.model.ControlFilter
import com.example.tooldatabase.model.FilterToolBody
import com.example.tooldatabase.model.NameType
import kotlinx.coroutines.flow.Flow

class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    private fun getDistinctRoomRawQuery(filterToolBody: FilterToolBody, forFieldName: String): RoomRawQuery {
        val whereArguments = mutableListOf<String>()
        var whereString = ""

        filterToolBody.fields.forEach() {
            if (it.value.filedName.value != forFieldName) {
                if (it.value.currentValue != null) {
                    whereArguments.add("${it.value.filedName.value} = '${it.value.currentValue}'")
                }
            }
        }

        if (whereArguments.isNotEmpty()) {
            whereString = "WHERE " +  whereArguments.joinToString(" AND ")
        }

        val roomRawQuery = RoomRawQuery(
            sql = "SELECT DISTINCT $forFieldName FROM tool_body $whereString ORDER BY $forFieldName ASC",
        )

        return roomRawQuery
    }

    fun updateAvailableValues(filterToolBody: FilterToolBody): FilterToolBody {
        val newFields: MutableMap<String, ControlFilter> = mutableMapOf()

        filterToolBody.fields.forEach() {
            val roomRawQuery = getDistinctRoomRawQuery(
                filterToolBody = filterToolBody,
                forFieldName = it.value.filedName.value
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value)
                    .copy(availableValues = toolBodyDao.getListValuesInt(roomRawQuery))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value)
                    .copy(availableValues = toolBodyDao.getListValuesDouble(roomRawQuery))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value)
                    .copy(availableValues = toolBodyDao.getListString(roomRawQuery))
            }
        }

        return filterToolBody.copy(fields = newFields)
    }

    fun updateValues(filterToolBody: FilterToolBody): FilterToolBody {
        val newFields: MutableMap<String, ControlFilter> = mutableMapOf()

        filterToolBody.fields.forEach() {
            val query = RoomRawQuery(
                sql = "SELECT DISTINCT ${it.value.filedName.value} FROM tool_body ORDER BY ${it.value.filedName.value} ASC",
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value)
                    .copy(values = toolBodyDao.getListValuesInt(query))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value)
                    .copy(values = toolBodyDao.getListValuesDouble(query))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value)
                    .copy(values = toolBodyDao.getListString(query))
            }
        }

        return filterToolBody.copy(fields = newFields)
    }

    fun getToolBodyListFlow(filterToolBody: FilterToolBody): Flow<List<ToolBody>> {
        val whereArguments = mutableListOf<String>()
        var whereString = ""

        filterToolBody.fields.forEach {
            if (it.value.currentValue != null) {
                whereArguments.add("${it.value.filedName.value} = '${it.value.currentValue}'")
            }
        }

        if (whereArguments.isNotEmpty()) {
            whereString = "WHERE " +  whereArguments.joinToString(" AND ")
        }

        val query = RoomRawQuery(
            sql = "SELECT * FROM tool_body $whereString",
        )

        return toolBodyDao.getToolBodyList(query)
    }

    fun getToolBodyById(id: Int): ToolBody {
        return toolBodyDao.getToolBodyById(id)
    }
}