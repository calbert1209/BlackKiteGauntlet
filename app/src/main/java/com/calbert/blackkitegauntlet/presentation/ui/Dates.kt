package com.calbert.blackkitegauntlet.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.calbert.blackkitegauntlet.R
import com.calbert.blackkitegauntlet.presentation.data.TidalEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateAdjust(
    onChange: (Int) -> Unit,
    onReset: () -> Unit
) {
    Row (
        modifier = Modifier.height(ButtonDefaults.ExtraSmallButtonSize)
    ) {
        Button(
            modifier = Modifier.size(ButtonDefaults.ExtraSmallButtonSize),
            shape = RoundedCornerShape(50),
            onClick = {
                onChange(-1)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.round_remove_24),
                contentDescription = "reduce date",
                tint = Color.White
            )
        }
        Button(
            modifier = Modifier.size(ButtonDefaults.ExtraSmallButtonSize),
            shape = RoundedCornerShape(50),
            onClick = {
                onReset()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.round_today_24),
                contentDescription = "reset to today",
                tint = Color.White
            )
        }
        Button(
            modifier = Modifier.size(ButtonDefaults.ExtraSmallButtonSize),
            shape = RoundedCornerShape(50),
            onClick = {
                onChange(1)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.round_add_24),
                contentDescription = "increase date",
                tint = Color.White
            )
        }
    }
}
@Composable
fun DateText(epochDays: Long) {
    val formatter = DateTimeFormatter.ofPattern("MM / dd")

    Text(text = LocalDate.ofEpochDay(epochDays).format(formatter), fontSize = 18.sp, modifier = Modifier)
}

@Composable
fun TidalExtreme(event: TidalEvent) {
    val symbol = if (event.type == "high") {"▲"} else {"▼"}

    Text(text = "${symbol}: ${event.level}")
}
