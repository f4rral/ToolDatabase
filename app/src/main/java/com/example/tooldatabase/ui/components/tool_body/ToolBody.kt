package com.example.contactsdb.ui.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tooldatabase.data.ToolBody
import com.example.tooldatabase.ui.theme.ThemeColor
import com.example.tooldatabase.ui.theme.ToolDatabaseTheme


class ToolBody {
    companion object {
        val toolBodyListFake = listOf(
            ToolBody(id = 0, ORDCODE = "MT100-012W16R01RD08", nmlDiameter = 12, ZEFP = 1),
            ToolBody(id = 1, ORDCODE = "MT100-012W16R01RD08", nmlDiameter = 12, ZEFP = 1),
            ToolBody(id = 2, ORDCODE = "MT100-012W16R01RD08", nmlDiameter = 12, ZEFP = 1)
        )
    }
}

@Composable
fun ToolBodyItem(
    item: ToolBody = ToolBody(id = 0, ORDCODE = "MT100-012W16R01RD08", nmlDiameter = 12, ZEFP = 1),
    onClick: ((id: Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp)),
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
                    .background(ThemeColor.violet3)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxHeight()

            ) {
                Text(
                    text = item.ORDCODE,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    lineHeight = 19.sp,
                    color = ThemeColor.gray7,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "id: ${item.id.toString()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 17.sp,
                        color = ThemeColor.gray5
                    )

                    Text(
                        text = "D: ${item.nmlDiameter.toString()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 17.sp,
                        color = ThemeColor.gray5
                    )

                    Text(
                        text = "z: ${item.ZEFP.toString()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 17.sp,
                        color = ThemeColor.gray5
                    )
                }

            }
        }
    }
}

@Composable
fun ToolBodyDetail(
    item: ToolBody,
    onDelete: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
                .background(ThemeColor.violet3)
        )

        Text(
            text = item.ORDCODE,
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = ThemeColor.gray7,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        Text(
            text = item.id.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
            color = ThemeColor.gray5,
        )
    }
}

@Composable
fun ToolBodyList(
    toolBodyList: List<ToolBody> = com.example.contactsdb.ui.contact.ToolBody.toolBodyListFake,
    onClickItem: ((id: Int) -> Unit)? = null
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
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
            toolBodyList = com.example.contactsdb.ui.contact.ToolBody.toolBodyListFake
        )
    }
}

@Preview
@Composable
private fun ToolBodyItemPreview() {
    ToolDatabaseTheme {
        ToolBodyItem(item = com.example.contactsdb.ui.contact.ToolBody.toolBodyListFake[0])
    }
}

@Preview
@Composable
private fun ContactDetailPreview() {
    ToolDatabaseTheme {
        ToolBodyDetail(item = com.example.contactsdb.ui.contact.ToolBody.toolBodyListFake[0])
    }
}
