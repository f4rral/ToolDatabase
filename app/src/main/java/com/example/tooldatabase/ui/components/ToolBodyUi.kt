package com.example.tooldatabase.ui.components.tool_body

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.tooldatabase.R
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
        "Типоразмер пластины" to toolBody.typeSizeInserts.getList().joinToString(" | "),
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

//        AsyncImage(
//            model = "file:///android_asset/images/no_image.png",
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(240.dp)
//                .padding(bottom = 16.dp)
//        )

        ToolBodyGallery(
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
                        text = if (value != null) "$value" else "-",
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

@Composable
fun ToolBodyGallery(
    modifier: Modifier = Modifier
) {
    val componentModifier = Modifier.wrapContentWidth().then(modifier)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = componentModifier
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(300.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(ThemeColor.gray5)
            ) {

                AsyncImage(
                    model = "file:///android_asset/images/no_image.png",
                    contentDescription = null,
                    modifier = Modifier
                        .width(240.dp)
                        .height(240.dp)
                )
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(300.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(ThemeColor.gray5)
            ) {
                AsyncImage(
                    model = "file:///android_asset/images/no_image.png",
                    contentDescription = null,
                    modifier = Modifier
                        .width(240.dp)
                        .height(240.dp)
                )
            }
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
fun ToolBodyGalleryPreview() {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(
            color = Color.Gray.toArgb(),
            width = 240,
            height = 240
        )
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        ToolDatabaseTheme {
            ToolBodyGallery()
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun ToolBodyDetailPreview() {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(
            color = Color.Gray.toArgb(),
            width = 240,
            height = 240
        )
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        ToolDatabaseTheme {
            ToolBodyDetail(toolBody = ToolBodyFakeData.toolBody1)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun ToolBodyDetailPreview2() {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(
            color = Color.Gray.toArgb(),
            width = 240,
            height = 240
        )
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        ToolDatabaseTheme {
            ToolBodyDetail(toolBody = ToolBodyFakeData.toolBody2)
        }
    }
}
