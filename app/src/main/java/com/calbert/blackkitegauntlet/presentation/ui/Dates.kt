package com.calbert.blackkitegauntlet.presentation.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.OutlinedButton
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
fun DateText(epochDays: Long, onClick: () -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("MM / dd")
    Button(
        modifier = Modifier.width(80.dp).padding(top = 16.dp),
        shape = RoundedCornerShape(percent = 50),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = Color.White
        )
    ) {
        Text(text = LocalDate.ofEpochDay(epochDays).format(formatter), fontSize = 18.sp)
    }

}

@Composable
fun TidalExtremes(events: List<TidalEvent>?) {
    if (events != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            events.forEach {event ->
                TidalExtreme(event = event)
            }
        }
    }
}

@Composable
fun TidalExtreme(event: TidalEvent) {
    val symbol = if (event.type == "high") {"▲"} else {"▼"}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,

    ) {
        Text(modifier = Modifier.width(24.dp), text = symbol)
        Text(modifier = Modifier.width(40.dp), text = "${event.level}")
        Text(modifier = Modifier.width(54.dp), text = event.timestamp.substring(11, 16))
    }
}
