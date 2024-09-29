package com.calbert.blackkitegauntlet.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.calbert.blackkitegauntlet.presentation.data.TidalEvent
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TideLevelChart(events: List<TidalEvent>, currentDate: Long) {
    Canvas(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
    ) {
        val brush = Brush.verticalGradient(
            colors = listOf(strongBlue, strongGreen)
        )
        events.indices.forEach { i ->
            val event = events[i]
            val level = event.level.toFloat();
            drawRect(
                topLeft = Offset((i * 10f), 160f - level),
                brush = brush,
                size = Size(width = 9f, height = level)
            )
        }
        if (LocalDate.now().toEpochDay() == currentDate) {
            val time = LocalTime.now().toSecondOfDay()
            val offsetX = time.toFloat() / 24f / 60f / 60f * 240f
            drawRect(
                topLeft = Offset(offsetX - 1f, 0f),
                color = Color.Red,
                size = Size(3f, 180f)
            )
        }
    }
}