package com.example.tooldatabase.ui.elements

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tooldatabase.ui.theme.ThemeColor


val buttonColors = ButtonColors(
    contentColor = ThemeColor.gray2,
    containerColor = ThemeColor.violet3,
    disabledContentColor = ThemeColor.gray4,
    disabledContainerColor = ThemeColor.gray5
)

@Composable
fun ButtonText(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    text: String = "button.text",
) {
    val buttonModifier = Modifier.wrapContentWidth().then(modifier)

    Button(
        colors = buttonColors,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            if (onClick != null) {
                onClick()
            }
        },
        modifier = buttonModifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
    }
}

@Preview
@Composable
fun ButtonTextPreview() {
    ButtonText()
}