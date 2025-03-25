package com.example.tooldatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tool_body")
data class ToolBody(
    @PrimaryKey
    var id: Int? = null,
    var title: String? = "",
    var series: String? = "",
    var KAPR: Double? = null,
    var ORDCODE: String,
    var nmlDiameter: Double,
    var ZEFP: Int,
)

class ToolBodyFakeData {
    companion object {
        val toolBodyFake = ToolBody(
            id = 0,
            title = "Концевая фреза с круглыми СМП",
            series = "MT1",
            KAPR = 0.0,
            ORDCODE = "MT100-012W16R01RD08",
            nmlDiameter = 12.0,
            ZEFP = 1,
        )

        val toolBodyListFake = listOf(
            toolBodyFake,
            toolBodyFake,
            toolBodyFake,
            toolBodyFake
        )
    }
}
