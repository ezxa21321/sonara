package com.sonora.music.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val SonoraGreen = Color(0xFF1ED760)
val Ink = Color(0xFF0A0A0C)
val InkSoft = Color(0xFF121215)
val Card = Color(0xFF16161A)
val CardHigh = Color(0xFF232329)
val TextDim = Color(0xFFAEAEB8)
val TextFaint = Color(0xFF7C7C88)
val Hairline = Color(0x1FFFFFFF)

private val SonoraColorScheme = darkColorScheme(
    primary = SonoraGreen,
    onPrimary = Color(0xFF04170A),
    primaryContainer = Color(0xFF0B3B1F),
    onPrimaryContainer = Color(0xFFB9F5CD),
    secondary = SonoraGreen,
    onSecondary = Color.Black,
    background = Ink,
    onBackground = Color.White,
    surface = Card,
    onSurface = Color.White,
    surfaceVariant = CardHigh,
    onSurfaceVariant = TextDim,
    surfaceContainer = InkSoft,
    surfaceContainerHigh = Card,
    surfaceContainerHighest = CardHigh,
    outline = Color(0xFF3A3A44),
    outlineVariant = Hairline,
    error = Color(0xFFFF5C63),
    onError = Color.White
)

private val SonoraTypography = Typography(
    displaySmall = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.8).sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.3).sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 24.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 21.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    bodyLarge = TextStyle(fontSize = 15.sp, lineHeight = 21.sp),
    bodyMedium = TextStyle(fontSize = 13.5.sp, lineHeight = 18.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 13.sp, letterSpacing = 0.1.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 11.5.sp, letterSpacing = 0.2.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 10.sp, letterSpacing = 0.4.sp)
)

private val SonoraShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun SonoraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SonoraColorScheme,
        typography = SonoraTypography,
        shapes = SonoraShapes,
        content = content
    )
}
