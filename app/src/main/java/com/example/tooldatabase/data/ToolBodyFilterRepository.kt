package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import kotlinx.coroutines.flow.Flow

class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    private fun getDistinctRoomRawQuery(filter: Filter, forFieldName: String): RoomRawQuery {
        val whereArguments = mutableListOf<String>()
        var whereString = ""

        filter.fields.forEach() {
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

    fun updateAvailableValues(filter: Filter): Filter {
        val newFields: MutableMap<String, ControlFilter> = mutableMapOf()

        filter.fields.forEach() {
            val roomRawQuery = getDistinctRoomRawQuery(
                filter = filter,
                forFieldName = it.value.filedName.value
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value)
                    .copy(availableValues = toolBodyDao.getListInt(roomRawQuery))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value)
                    .copy(availableValues = toolBodyDao.getListDouble(roomRawQuery))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value)
                    .copy(availableValues = toolBodyDao.getListString(roomRawQuery))
            }
        }

        return filter.copy(fields = newFields)
    }

    fun updateValues(filter: Filter): Filter {
        val newFields: MutableMap<String, ControlFilter> = mutableMapOf()

        filter.fields.forEach() {
            val query = RoomRawQuery(
                sql = "SELECT DISTINCT ${it.value.filedName.value} FROM tool_body ORDER BY ${it.value.filedName.value} ASC",
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value)
                    .copy(values = toolBodyDao.getListInt(query))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value)
                    .copy(values = toolBodyDao.getListDouble(query))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value)
                    .copy(values = toolBodyDao.getListString(query))
            }
        }

        return filter.copy(fields = newFields)
    }

    fun getToolBodyListFlow(filter: Filter): Flow<List<ToolBody>> {
        val whereArguments = mutableListOf<String>()
        var whereString = ""

        filter.fields.forEach {
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
}

data class Filter (
    val fields: MutableMap<String, ControlFilter> = mutableMapOf(
        NameField.NML_DIAMETER.name to ControlFilter(
            filedName = NameField.NML_DIAMETER,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.DOUBLE
        ),
        NameField.ZEFP.name to ControlFilter(
            filedName = NameField.ZEFP,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.INT
        ),
        NameField.SERIES.name to ControlFilter(
            filedName = NameField.SERIES,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.STRING
        )
    )
)

data class ControlFilter(
    val filedName: NameField,
    var currentValue: Any? = null,
    var values: List<Any> = listOf(),
    var availableValues: List<Any> = listOf(),
    val typeData: NameType
)

enum class NameField(val value: String) {
    NML_DIAMETER(value = "nmlDiameter"),
    ZEFP(value = "ZEFP"),
    SERIES(value = "series")
}

enum class NameType {
    INT,
    DOUBLE,
    STRING,
}