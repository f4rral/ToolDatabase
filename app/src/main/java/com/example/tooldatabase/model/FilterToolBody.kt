package com.example.tooldatabase.model

data class FilterToolBody (
    val fields: MutableMap<String, ControlFilter> = mutableMapOf(
        NameField.NML_DIAMETER.name to ControlFilter(
            filedName = NameField.NML_DIAMETER,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.DOUBLE
        ),
        NameField.ZEFP.name to ControlFilter(
            filedName = NameField.ZEFP,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.INT
        ),
        NameField.SERIES.name to ControlFilter(
            filedName = NameField.SERIES,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.STRING
        )
    )
)

data class ControlFilter(
    val filedName: NameField,
    var currentValue: Any? = null,
    var values: List<Any> = listOf(),
    var availableValues: List<Any> = listOf(),
    val typeData: NameType
)

enum class NameField(val value: String) {
    NML_DIAMETER(value = "nmlDiameter"),
    ZEFP(value = "ZEFP"),
    SERIES(value = "series")
}

enum class NameType {
    INT,
    DOUBLE,
    STRING,
}