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