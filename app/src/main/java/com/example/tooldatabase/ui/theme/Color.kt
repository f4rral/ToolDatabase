package com.example.tooldatabase.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object ThemeColor {
    val gray1 = Color(0xFFFFFFFF)
    val gray2 = Color(0xFFF7F6FA)
    val gray3 = Color(0xFFEEEAF5)
    val gray4 = Color(0xFFE2DEE9)
    val gray5 = Color(0xFFA8A6AC)
    val gray6 = Color(0xFF625F68)
    val gray7 = Color(0xFF2A282F)
    val violet1 = Color(0xFFF4F0FF)
    val violet2 = Color(0xFFE9E1FF)
    val violet3 = Color(0xFF7949FF)
    val yellow1 = Color(0xFFF5BE2E)
    val green1 = Color(0xFF45A320)

    val buttonColors: ButtonColors = ButtonColors(
        contentColor = gray2,
        containerColor = violet3,
        disabledContentColor = gray4,
        disabledContainerColor = gray5
    )
}
