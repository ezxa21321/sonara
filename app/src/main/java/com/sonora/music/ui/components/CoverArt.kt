package com.sonora.music.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

/**
 * Процедурная обложка: градиент + мягкие пятна света и диагональный блик.
 * Никаких сетевых картинок — рисуем сами, поэтому приложение работает офлайн.
 */
@Composable
fun CoverArt(
    start: Color,
    end: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    seed: Int = 0,
    label: String? = null,
    labelAlpha: Float = 0.22f
) {
    BoxWithConstraints(
        modifier = modifier
            .clip(shape)
            .background(Brush.linearGradient(listOf(start, end)))
    ) {
        val shift = (seed % 5) / 5f

        Canvas(Modifier.matchParentSize()) {
            val side = min(size.width, size.height)
            drawCircle(
                color = Color.White.copy(alpha = 0.16f),
                radius = side * 0.55f,
                center = Offset(size.width * (0.92f - 0.1f * shift), size.height * 0.06f)
            )
            drawCircle(
                color = Color.Black.copy(alpha = 0.20f),
                radius = side * 0.5f,
                center = Offset(size.width * 0.06f, size.height * (0.98f + 0.04f * shift))
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.07f),
                radius = side * 0.26f,
                center = Offset(size.width * 0.68f, size.height * 0.78f)
            )
            val streak = Path().apply {
                moveTo(-size.width * 0.15f, size.height * 0.82f)
                lineTo(size.width * 0.62f, -size.height * 0.18f)
                lineTo(size.width * 0.80f, -size.height * 0.18f)
                lineTo(size.width * 0.03f, size.height * 0.82f)
                close()
            }
            drawPath(streak, Color.White.copy(alpha = 0.09f))
        }

        if (label != null) {
            Text(
                text = label.trim().take(1).uppercase(),
                color = Color.White.copy(alpha = labelAlpha),
                fontSize = (maxWidth.value * 0.46f).sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 6.dp, bottom = 0.dp)
            )
        }
    }
}
