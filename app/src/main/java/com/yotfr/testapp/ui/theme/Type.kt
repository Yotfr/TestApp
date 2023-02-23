package com.yotfr.testapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yotfr.testapp.R

private val Circe = FontFamily(
    fonts = listOf(
        Font(R.font.circelight, FontWeight.W300),
        Font(R.font.circeregular, FontWeight.W400),
        Font(R.font.circebold, FontWeight.W500)
    )
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W500,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W500,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W500,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W500,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 21.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W400,
        fontSize = 21.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W400,
        fontSize = 17.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 17.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Circe,
        fontWeight = FontWeight.W300,
        fontSize = 11.sp
    )
)