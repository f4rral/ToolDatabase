package com.example.tooldatabase.data.db.tool_body

import androidx.room.ColumnInfo
import androidx.room.Embedded
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

    @Embedded
    var typeSizeInserts: TypeSizeInsert = TypeSizeInsert()
)

data class TypeSizeInsert(
    @ColumnInfo(name = "MIID_0")
    var insert1: String? = null,

    @ColumnInfo(name = "MIID_1")
    var insert2: String? = null,
) {
    fun getList(): List<String?> {
        val insertsList = mutableListOf(insert1)

        if (insert2 != null) {
            insertsList.add(insert2)
        }

        return insertsList
    }
}

class ToolBodyFakeData {
    companion object {
        val toolBody1 = ToolBody(
            id = 0,
            title = "Концевая фреза с круглыми СМП",
            orderCode = "MT100-012W16R01RD08",
            series = "MT1",
            kapr = 0.0,
            cuttingPart = null,
            zefp = 1,
            dcon = 16.0,
            apMax = 4.0,
            nmlDiameter = 12.0,
            directionCutting = "R",
            formFixPart = "W",
            sizeFixPart = 16,
            isDrilling = 0,
            isPlunger = 0,
            isAerospace = 0,
            isTitan = 0,
            isAluminum = 0,
            nMax = 30000.0,
            weight = 0.2,
            isCoolantHoles = 0,
            typeSizeInserts = TypeSizeInsert(
                insert1 = "RD08"
            )
        )

        val toolBody2 = ToolBody(
            id = 2052,
            title = "Концевая фреза 90° удлиненная с коротким рабочим вылетом с внутренней подачей СОЖ",
            orderCode = "MT190-016Z16R02BD10-H025-L090-IK",
            series = "MT1",
            kapr = 0.0,
            cuttingPart = null,
            zefp = 2,
            dcon = 16.0,
            apMax = 10.0,
            nmlDiameter = 16.0,
            directionCutting = "R",
            formFixPart = "Z",
            sizeFixPart = 16,
            isDrilling = 0,
            isPlunger = 0,
            isAerospace = 0,
            isTitan = 0,
            isAluminum = 0,
            nMax = 49200.0,
            weight = 0.1,
            isCoolantHoles = 1,
            typeSizeInserts = TypeSizeInsert(
                insert1 = "BD10"
            )
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
