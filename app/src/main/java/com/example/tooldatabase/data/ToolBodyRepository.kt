package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import kotlinx.coroutines.flow.Flow

class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    fun getToolBodyList(filter: Filter): Flow<List<ToolBody>> {
        val argumentsArr = mutableListOf<String>()
        var str = ""

        filter.fields.forEach {
            if (it.value.currentValue != null) {
                argumentsArr.add("${it.value.name} = '${it.value.currentValue}'")
            }
        }

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        val query = RoomRawQuery(
            sql = "SELECT * FROM tool_body $str",
        )

        return toolBodyDao.getToolBodyList(query)
    }

    fun getUpdateAvailableValues2(filter: Filter, fieldName: String): RoomRawQuery {
        val argumentsArr = mutableListOf<String>()
        var str = ""

        filter.fields.forEach() {
            if (it.value.name != fieldName) {
                if (it.value.currentValue != null) {
                    argumentsArr.add("${it.value.name} = '${it.value.currentValue}'")
                }
            }
        }

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        val query = RoomRawQuery(
            sql = "SELECT DISTINCT $fieldName FROM tool_body $str ORDER BY $fieldName ASC",
        )

        return query
    }

    fun updateAvailableValues(filter: Filter): Filter {
        val newFields: MutableMap<String, ControlFilter<out Any>> = mutableMapOf()

        filter.fields.forEach() {
            val sqlQuery = getUpdateAvailableValues2(
                filter = filter,
                fieldName = it.value.name
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value as ControlFilter<Int>)
                    .copy(availableValues = toolBodyDao.getListInt(sqlQuery))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value as ControlFilter<Double>)
                    .copy(availableValues = toolBodyDao.getListDouble(sqlQuery))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value as ControlFilter<String>)
                    .copy(availableValues = toolBodyDao.getListString(sqlQuery))
            }
        }

        return filter.copy(fields = newFields)
    }

    fun updateValues(filter: Filter): Filter {
        val newFields: MutableMap<String, ControlFilter<out Any>> = mutableMapOf()

        filter.fields.forEach() {
            val query = RoomRawQuery(
                sql = "SELECT DISTINCT ${it.value.name} FROM tool_body ORDER BY ${it.value.name} ASC",
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value as ControlFilter<Int>)
                    .copy(values = toolBodyDao.getListInt(query))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value as ControlFilter<Double>)
                    .copy(values = toolBodyDao.getListDouble(query))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value as ControlFilter<String>)
                    .copy(values = toolBodyDao.getListString(query))
            }
        }

        return filter.copy(fields = newFields)
    }
}

data class Filter (
    val fields: MutableMap<String, ControlFilter<out Any>> = mutableMapOf(
        NameField.NML_DIAMETER.name to ControlFilter<Double>(
            name = NameField.NML_DIAMETER.value,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.DOUBLE
        ),
        NameField.ZEFP.name to ControlFilter<Int>(
            name = NameField.ZEFP.value,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.INT
        ),
        NameField.SERIES.name to ControlFilter<String>(
            name = NameField.SERIES.value,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.STRING
        )
    )
)

data class ControlFilter<T>(
    val name: String,
    var currentValue: T? = null,
    var values: List<T> = listOf(),
    var availableValues: List<T> = listOf(),
    val typeData:NameType
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