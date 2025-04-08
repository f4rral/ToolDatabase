package com.example.tooldatabase.ui.components.tool_body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tooldatabase.data.db.tool_body.ToolBody
import com.example.tooldatabase.data.db.tool_body.ToolBodyFakeData
import com.example.tooldatabase.ui.theme.ThemeColor
import com.example.tooldatabase.ui.theme.ToolDatabaseTheme


@Composable
fun ToolBodyItem(
    item: ToolBody = ToolBody(
        id = -1,
        title = "",
        series = "",
        kapr = 0.0,
        orderCode = "",
        nmlDiameter = 0.0,
        zefp = 0
    ),
    onClick: ((id: Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(
            containerColor = ThemeColor.gray2,
        ),
        onClick = {
            if (onClick != null) {
                onClick(item.id!!)
            }
        }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Max)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 48.dp, height = 48.dp)
                    .clip(shape = RoundedCornerShape(percent = 50))
                    .background(ThemeColor.red1)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()

            ) {
                Text(
                    text = item.orderCode,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    lineHeight = 18.sp,
                    color = ThemeColor.gray7,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "D фрезы: ${item.nmlDiameter}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 16.sp,
                        color = ThemeColor.gray5
                    )
                }

            }
        }
    }
}

@Composable
fun ToolBodyDetail(
    toolBody: ToolBody
) {
    val mainParameters = mapOf(
        "Серия" to toolBody.series,
        "Угол в плане, °" to toolBody.kapr,
        "Номинальный диаметр, мм" to toolBody.nmlDiameter,
        "Макс. глубина резания, мм" to toolBody.apMax,
        "Число зубъев, шт" to toolBody.zefp,
        "Форма крепежной части" to toolBody.formFixPart,
        "Размер крепежной части" to toolBody.sizeFixPart,
        "Типоразмер пластины" to "${toolBody.MIID_0} | ${toolBody.MIID_1}",
        "Каналы для подачи СОЖ" to toolBody.isCoolantHoles,
        "Макс. скорость вращения, об/мин" to toolBody.nMax,
        "Масса, кг" to toolBody.weight,
        "Направление резания" to toolBody.directionCutting
    )

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(ThemeColor.gray2)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(shape = RoundedCornerShape(percent = 50))
                .size(width = 64.dp, height = 64.dp)
                .background(ThemeColor.red1)
        )

        Text(
            text = toolBody.orderCode,
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = ThemeColor.gray7,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        Text(
            text = toolBody.title!!,
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            lineHeight = 18.sp,
            color = ThemeColor.gray5,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Характеристики:",
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = ThemeColor.gray7,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for ((key, value) in mainParameters) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = key,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 18.sp,
                        color = ThemeColor.gray5,
                        modifier = Modifier
                            .weight(0.7f)
                    )

                    Text(
                        text = "$value",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        lineHeight = 18.sp,
                        color = ThemeColor.gray7,
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .weight(0.3f)
                    )
                }
            }
        }
    }
}

@Composable
fun ToolBodyList(
    toolBodyList: List<ToolBody> = listOf(),
    onClickItem: ((id: Int) -> Unit)? = null
) {
    LazyColumn(
//        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(toolBodyList) {
            ToolBodyItem(
                item = it,
                onClick = onClickItem
            )
        }
    }
}

@Preview
@Composable
private fun ToolBodyPreview() {
    ToolDatabaseTheme {
        ToolBodyList(
            toolBodyList = ToolBodyFakeData.toolBodyListFake
        )
    }
}

@Preview
@Composable
private fun ToolBodyItemPreview() {
    ToolDatabaseTheme {
        ToolBodyItem(item = ToolBodyFakeData.toolBody1)
    }
}

@Preview
@Composable
private fun ToolBodyDetailPreview() {
    ToolDatabaseTheme {
        ToolBodyDetail(toolBody = ToolBodyFakeData.toolBody1)
    }
}

@Preview
@Composable
private fun ToolBodyDetailPreview2() {
    ToolDatabaseTheme {
        ToolBodyDetail(toolBody = ToolBodyFakeData.toolBody2)
    }
}

