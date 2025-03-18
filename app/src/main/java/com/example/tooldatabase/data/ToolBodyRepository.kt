package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import com.example.tooldatabase.viewmodels.Filter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest


class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    suspend fun updateToolBody(toolBody: ToolBody) {
        toolBodyDao.updateToolBody(toolBody)
    }

    fun filterQuery(filter: Filter): Flow<List<ToolBody>> {
        val nmlDiameter: Double? = filter.nmlDiameter
        val ZEFP: Int? = filter.ZEFP
        val argumentsArr = mutableListOf<String>()
        var str = ""

        if (nmlDiameter != null) {
            argumentsArr.add("nmlDiameter = $nmlDiameter")
        }

        if (ZEFP != null) {
            argumentsArr.add("ZEFP = $ZEFP")
        }

        println("ToolBodyRepository $argumentsArr")

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        println("rawQuery str: $str")

        val query = RoomRawQuery(
            sql = "SELECT * FROM tool_body $str",
        )

        return toolBodyDao.rawQuery(query)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAvailableFilters(): Flow<AvailableFilters> {
        val flowAF: Flow<AvailableFilters> = toolBodyDao.getAllNmlDiameter()
            .mapLatest { AvailableFilters(it) }

        return flowAF
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAvailableFilters2(): Flow<AvailableFilters> {
        return combine(
            toolBodyDao.getAllNmlDiameter(),
            toolBodyDao.getAllZEFP()
        ) { allNmlDiameter, allZEFP ->
            AvailableFilters(
                allNmlDiameter = allNmlDiameter,
                allZEFP = allZEFP
            )
        }
    }
}

data class AvailableFilters(
    val allNmlDiameter: List<Double> = listOf(),
    val allZEFP: List<Int> = listOf()
)