package com.example.mysaaproject.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R

// Montserrat family recovered from the SAA design (weights 300/400/500)
val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
)

// Material typography rebased on Montserrat
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)
