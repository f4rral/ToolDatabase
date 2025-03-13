package com.example.tooldatabase.data

import androidx.room.RoomRawQuery
import com.example.tooldatabase.viewmodels.Filter
import kotlinx.coroutines.flow.Flow

class ToolBodyRepository(private var toolBodyDao: ToolBodyDao) {
    fun getAll(): Flow<List<ToolBody>> {
        return toolBodyDao.getAll()
    }

    fun getToolBodyByDiameter(nmlDiameter: Int?): Flow<List<ToolBody>> {
        return if (nmlDiameter != null) {
            toolBodyDao.getToolBodyByDiameter(nmlDiameter)
        } else {
            toolBodyDao.getAll()
        }
    }

//    suspend fun getUniqueNmlDiameter(): List<Double> {
//        return toolBodyDao.nmlDiameterUnique()
//    }

//    fun getUniqueNmlDiameterFlow(): Flow<List<Double>> {
//        return toolBodyDao.nmlDiameterUniqueFlow()
//    }

    suspend fun update(toolBody: ToolBody) {
        toolBodyDao.update(toolBody)
    }

    suspend fun filterQuery(filter: Filter): Flow<List<ToolBody>> {
        val nmlDiameter: Double? = filter.nmlDiameter
        val argumentsArr = mutableListOf<String>()
        var str = ""

        if (nmlDiameter != null) {
            argumentsArr.add("nmlDiameter = $nmlDiameter")
        }

        if (argumentsArr.isNotEmpty()) {
            str = "WHERE " +  argumentsArr.joinToString(" AND ")
        }

        println("rawQuery str: $str")

        val query = RoomRawQuery(
            sql = "SELECT * FROM tool_body $str",
        )

        return toolBodyDao.rawQuery(query)
    }
}