package com.example.tooldatabase.models.filter

data class FilterToolBody (
    val fields: MutableMap<String, FieldFilter> = mutableMapOf(
        NameField.NML_DIAMETER.name to FieldFilter(
            filedName = NameField.NML_DIAMETER,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.DOUBLE
        ),
        NameField.ZEFP.name to FieldFilter(
            filedName = NameField.ZEFP,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.INT
        ),
        NameField.SERIES.name to FieldFilter(
            filedName = NameField.SERIES,
            values = listOf(),
            availableValues = listOf(),
            currentValue = null,
            typeData = NameType.STRING
        )
    )
)

data class FieldFilter(
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