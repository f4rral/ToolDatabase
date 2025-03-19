package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import com.example.tooldatabase.viewmodels.Filter
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

        println("ToolDataBaseApp getToolBodyList $argumentsArr")

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        val query = RoomRawQuery(
            sql = "SELECT * FROM tool_body $str",
        )

        return toolBodyDao.getToolBodyList(query)
    }

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

        println("ToolDataBaseApp getAvailableValues $argumentsArr")

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