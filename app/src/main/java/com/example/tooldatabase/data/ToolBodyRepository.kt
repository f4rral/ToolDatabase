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
    fun getAvailableValues(filter: Filter, fieldName: String): Flow<List<Int>> {
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

        val flow: Flow<List<Int>> = toolBodyDao.rawQuery(query)
            .mapLatest { it }

        return flow
    }

    fun getAvailableValues2(filter: Filter2) {
        filter.fields.forEach {
            println("ToolDataBaseApp ${it.key} ${it.value.name}")

            val query = RoomRawQuery(
                sql = "SELECT DISTINCT ${it.value.name} FROM tool_body ORDER BY ${it.value.name} ASC",
            )

            if (it.value.typeData == NameType.INT) {
                println("ToolDataBaseApp ${toolBodyDao.getAllValuesInt(query)}")
                (it.value as ControlFilter2<Int>).values = toolBodyDao.getAllValuesInt(query)
            }

            if (it.value.typeData == NameType.DOUBLE) {
                println("ToolDataBaseApp ${toolBodyDao.getAllValuesDouble(query)}")
                (it.value as ControlFilter2<Double>).values = toolBodyDao.getAllValuesDouble(query)
            }

            if (it.value.typeData == NameType.STRING) {
                println("ToolDataBaseApp ${toolBodyDao.getAllValuesString(query)}")
                (it.value as ControlFilter2<String>).values = toolBodyDao.getAllValuesString(query)
            }
        }

        println("ToolDataBaseApp ${filter.fields}")
    }

    fun getAvailableFilters(filter: Filter): Flow<AvailableFilters> {
        return combine(
            toolBodyDao.getAllNmlDiameter(),
            toolBodyDao.getAllZEFP(),
            toolBodyDao.getAllSeries(),
            getAvailableValues(filter, "ZEFP")
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

class Filter2 {
    val fields: Map<String, ControlFilter2<out Any>> = mapOf(
        NameField.NML_DIAMETER.name to ControlFilter2<Double>(
            name = NameField.NML_DIAMETER.value,
            values = emptyList(),
            availableValues = emptyList(),
            currentValue = null,
            typeData = NameType.DOUBLE
        ),
        NameField.ZEFP.name to ControlFilter2<Int>(
            name = NameField.ZEFP.value,
            values = emptyList(),
            availableValues = emptyList(),
            currentValue = null,
            typeData = NameType.INT
        ),
        NameField.SERIES.name to ControlFilter2<String>(
            name = NameField.SERIES.value,
            values = emptyList(),
            availableValues = emptyList(),
            currentValue = null,
            typeData = NameType.STRING
        )
    )
}

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
    var values: List<T> = emptyList(),
    var availableValues: List<T> = emptyList(),
    val typeData:NameType
)

data class ControlValue2<T>(
    val value: T,
    val isActive: Boolean = true
)