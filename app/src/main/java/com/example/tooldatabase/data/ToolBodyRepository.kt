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

    fun getToolBodyList(filter: Filter): Flow<List<ToolBody>> {
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

        return toolBodyDao.getToolBodyList(query)
    }

    fun rawQuery(): Flow<List<Int>> {
        val flow: Flow<List<Int>> = toolBodyDao.rawQuery(RoomRawQuery(sql = """ 
            SELECT DISTINCT ZEFP FROM tool_body 
            WHERE nmlDiameter = 50 AND series = "MT2"
            ORDER BY ZEFP ASC
        """))
            .mapLatest { it }

        return flow
    }

    fun getAvailableFilters(): Flow<AvailableFilters> {
        return combine(
            toolBodyDao.getAllNmlDiameter(),
            toolBodyDao.getAllZEFP(),
            rawQuery()
        ) { allNmlDiameter, allZEFP, availableZEFP ->
            AvailableFilters(
                allNmlDiameter = allNmlDiameter,
                allZEFP = allZEFP,
                availableZEFP = availableZEFP
            )
        }
    }
}

data class AvailableFilters(
    val allNmlDiameter: List<Double> = listOf(),
    val allZEFP: List<Int> = listOf(),
    val availableZEFP: List<Int> = listOf()
)