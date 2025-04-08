package com.example.tooldatabase.data.db.tool_body

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tool_body")
data class ToolBody(
    @PrimaryKey
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String? = "",

    @ColumnInfo(name = "ORDCODE")
    var orderCode: String = "",

    @ColumnInfo(name = "series")
    var series: String? = "",

    @ColumnInfo(name = "KAPR")
    var kapr: Double? = null,

    @ColumnInfo(name = "cuttingPart")
    var cuttingPart: String? = null,

    @ColumnInfo(name = "ZEFP")
    var zefp: Int,

    @ColumnInfo(name = "DCON")
    var dcon: Double? = null,

    @ColumnInfo(name = "APMX")
    var apMax: Double? = null,

    @ColumnInfo(name = "nmlDiameter")
    var nmlDiameter: Double,

    @ColumnInfo(name = "directionCutting")
    var directionCutting: String? = null,

    @ColumnInfo(name = "formFixPart")
    var formFixPart: String? = null,

    @ColumnInfo(name = "sizeFixPart")
    var sizeFixPart: Int? = null,

    @ColumnInfo(name = "isDrilling")
    var isDrilling: Int? = 0,

    @ColumnInfo(name = "isPlunger")
    var isPlunger: Int? = 0,

    @ColumnInfo(name = "isAerospace")
    var isAerospace: Int? = 0,

    @ColumnInfo(name = "isTitan")
    var isTitan: Int? = 0,

    @ColumnInfo(name = "isAluminum")
    var isAluminum: Int? = 0,

    @ColumnInfo(name = "nMax")
    var nMax: Double? = null,

    @ColumnInfo(name = "weight")
    var weight: Double? = null,

    @ColumnInfo(name = "coolantHoles")
    var isCoolantHoles: Int? = 0,

    @ColumnInfo(name = "MIID_0")
    var MIID_0: String? = null,

    @ColumnInfo(name = "MIID_1")
    var MIID_1: String? = null,
)

class ToolBodyFakeData {
    companion object {
        val toolBody1 = ToolBody(
            id = 0,
            title = "Концевая фреза с круглыми СМП",
            series = "MT1",
            kapr = 0.0,
            orderCode = "MT100-012W16R01RD08",
            nmlDiameter = 12.0,
            zefp = 1,
        )

        val toolBody2 = ToolBody(
            id = 1,
            title = "Концевая фреза 90° удлиненная с коротким рабочим вылетом с внутренней подачей СОЖ",
            series = "MT1",
            kapr = 0.0,
            orderCode = "MT190-016Z16R02BD10-H025-L090-IK",
            nmlDiameter = 16.0,
            zefp = 2,
        )

        val toolBodyListFake = listOf(
            toolBody1,
            toolBody2,
            toolBody1,
            toolBody2,
            toolBody1,
            toolBody2,
            toolBody1,
            toolBody2,
        )
    }
}
