package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest

class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    suspend fun updateToolBody(toolBody: ToolBody) {
        toolBodyDao.updateToolBody(toolBody)
    }

    fun getToolBodyList(filter: Filter): Flow<List<ToolBody>> {
        val nmlDiameter: Double? = filter.nmlDiameter
        val ZEFP: Int? = filter.ZEFP
        val series: String? = filter.series
        val argumentsArr = mutableListOf<String>()
        var str = ""

        if (nmlDiameter != null) {
            argumentsArr.add("nmlDiameter = '$nmlDiameter'")
        }

        if (ZEFP != null) {
            argumentsArr.add("ZEFP = '$ZEFP'")
        }

        if (series != null) {
            argumentsArr.add("series = '$series'")
        }

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        val query = RoomRawQuery(
            sql = "SELECT * FROM tool_body $str",
        )

        return toolBodyDao.getToolBodyList(query)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAvailableValuesFlow(filter: Filter, fieldName: String): Flow<List<Int>> {
        val nmlDiameter: Double? = filter.nmlDiameter
        val ZEFP: Int? = filter.ZEFP
        val series: String? = filter.series

        val argumentsArr = mutableListOf<String>()
        var str = ""

        if (nmlDiameter != null  && fieldName != "nmlDiameter") {
            argumentsArr.add("nmlDiameter = '$nmlDiameter'")
        }

        if (series != null && fieldName != "series") {
            argumentsArr.add("series = '$series'")
        }

        if (ZEFP != null && fieldName != "ZEFP") {
            argumentsArr.add("ZEFP = '$ZEFP'")
        }

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        /*
        SELECT DISTINCT ZEFP FROM tool_body
        WHERE nmlDiameter = 50 AND series = "MT2"
        ORDER BY ZEFP ASC
        */

        val query = RoomRawQuery(
            sql = "SELECT DISTINCT $fieldName FROM tool_body $str ORDER BY ZEFP ASC",
        )

        val flow: Flow<List<Int>> = toolBodyDao.rawQueryFlow(query)
            .mapLatest { it }

        return flow
    }

    fun getUpdateAvailableValues2(filter: Filter2, fieldName: String): RoomRawQuery {
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

        println("ToolDataBaseApp Z $argumentsArr")

        val query = RoomRawQuery(
            sql = "SELECT DISTINCT $fieldName FROM tool_body $str ORDER BY $fieldName ASC",
        )


        return query
    }

    fun getAllUpdateAvailableValues2(filter: Filter2): Filter2 {
        val newFields: MutableMap<String, ControlFilter2<out Any>> = mutableMapOf()

        filter.fields.forEach() {
            val sqlQuery = getUpdateAvailableValues2(
                filter = filter,
                fieldName = it.value.name
            )

            if (it.value.typeData == NameType.INT) {
                newFields[it.key] = (it.value as ControlFilter2<Int>)
                    .copy(availableValues = toolBodyDao.getAllValuesInt(sqlQuery))
            }

            if (it.value.typeData == NameType.DOUBLE) {
                newFields[it.key] = (it.value as ControlFilter2<Double>)
                    .copy(availableValues = toolBodyDao.getAllValuesDouble(sqlQuery))
            }

            if (it.value.typeData == NameType.STRING) {
                newFields[it.key] = (it.value as ControlFilter2<String>)
                    .copy(availableValues = toolBodyDao.getAllValuesString(sqlQuery))
            }
        }

        println("ToolDataBaseApp Y $newFields")

        return filter.copy(fields = newFields)
    }

    fun getUpdateValues2(filter: Filter2): Filter2 {
        val newFields: MutableMap<String, ControlFilter2<out Any>> = mutableMapOf()

        filter.fields.forEach() {
//            println("ToolDataBaseApp ${it.key} ${it.value.name}")

            val query = RoomRawQuery(
                sql = "SELECT DISTINCT ${it.value.name} FROM tool_body ORDER BY ${it.value.name} ASC",
            )

            if (it.value.typeData == NameType.INT) {
//                println("ToolDataBaseApp ${toolBodyDao.getAllValuesInt(query)}")

                newFields[it.key] = (it.value as ControlFilter2<Int>)
                    .copy(values = toolBodyDao.getAllValuesInt(query))
            }

            if (it.value.typeData == NameType.DOUBLE) {
//                println("ToolDataBaseApp ${toolBodyDao.getAllValuesDouble(query)}")

                newFields[it.key] = (it.value as ControlFilter2<Double>)
                    .copy(values = toolBodyDao.getAllValuesDouble(query))
            }

            if (it.value.typeData == NameType.STRING) {
//                println("ToolDataBaseApp ${toolBodyDao.getAllValuesString(query)}")

                newFields[it.key] = (it.value as ControlFilter2<String>)
                    .copy(values = toolBodyDao.getAllValuesString(query))
            }
        }

        return filter.copy(fields = newFields)
    }

    fun getAvailableFilters(filter: Filter): Flow<AvailableFilters> {
        return combine(
            toolBodyDao.getAllNmlDiameter(),
            toolBodyDao.getAllZEFP(),
            toolBodyDao.getAllSeries(),
            getAvailableValuesFlow(filter, "ZEFP")
        ) { allNmlDiameter, allZEFP, allSeries, availableZEFP ->
            AvailableFilters(
                allNmlDiameter = allNmlDiameter,
                allZEFP = allZEFP,
                allSeries = allSeries,
                availableZEFP = availableZEFP
            )
        }
    }
}

data class AvailableFilters(
    val allNmlDiameter: List<Double> = listOf(),
    val allZEFP: List<Int> = listOf(),
    val allSeries: List<String> = listOf(),
    val availableZEFP: List<Int> = listOf(),
)

data class Filter(
    var nmlDiameter: Double? = null,
    var ZEFP: Int? = null,
    var series: String? = null
)

data class Filter2 (
    val fields: MutableMap<String, ControlFilter2<out Any>> = mutableMapOf(
        NameField.NML_DIAMETER.name to ControlFilter2<Double>(
            name = NameField.NML_DIAMETER.value,
            values = listOf(),
            availableValues = listOf(),
            currentValue = 50.0,
            typeData = NameType.DOUBLE
        ),
        NameField.ZEFP.name to ControlFilter2<Int>(
            name = NameField.ZEFP.value,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.INT
        ),
        NameField.SERIES.name to ControlFilter2<String>(
            name = NameField.SERIES.value,
            values = listOf(),
            availableValues = listOf(),
            currentValue = "MT1",
            typeData = NameType.STRING
        )
    )
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

data class ControlFilter2<T>(
    val name: String,
    var currentValue: T? = null,
    var values: List<T> = listOf(),
    var availableValues: List<T> = listOf(),
    val typeData:NameType
)

data class ControlValue2<T>(
    val value: T,
    val isActive: Boolean = true
)