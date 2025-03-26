package com.example.tooldatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tool_body")
data class ToolBody(
    @PrimaryKey
    var id: Int?,
    var title: String? = "",
    var ORDCODE: String = "",
    var series: String? = "",
    var KAPR: Double? = null,
    var cuttingPart: String? = null,
    var ZEFP: Int,
    var DCON: Double? = null,
    var APMX: Double? = null,
    var nmlDiameter: Double,
    var directionCutting: String? = null,
    var formFixPart: String? = null,
    var sizeFixPart: Int? = null,
    var isDrilling: Int? = 0,
    var isPlunger: Int? = 0,
    var isAerospace: Int? = 0,
    var isTitan: Int? = 0,
    var isAluminum: Int? = 0,
    var nMax: Double? = null,
    var weight: Double? = null,
    var coolantHoles: Int? = 0,
    var MIID_0: String? = null,
    var MIID_1: String? = null,
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

        val toolBodyFake2 = ToolBody(
            id = 1,
            title = "Концевая фреза 90° удлиненная с коротким рабочим вылетом с внутренней подачей СОЖ",
            series = "MT1",
            KAPR = 0.0,
            ORDCODE = "MT190-016Z16R02BD10-H025-L090-IK",
            nmlDiameter = 16.0,
            ZEFP = 2,
        )

        val toolBodyListFake = listOf(
            toolBodyFake,
            toolBodyFake2,
            toolBodyFake,
            toolBodyFake2
        )
    }
}
