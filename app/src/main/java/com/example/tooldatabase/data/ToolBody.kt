package com.example.tooldatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tool_body")
data class ToolBody(
    @PrimaryKey
    var id: Int? = null,
    var ordcode: String
)