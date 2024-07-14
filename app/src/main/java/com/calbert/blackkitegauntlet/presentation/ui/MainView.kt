package com.calbert.blackkitegauntlet.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import kotlin.math.abs

@Composable
fun MainView(
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val scrollState = rememberScalingLazyListState()
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current
    val state = viewModel.state().collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .onRotaryScrollEvent {
                val dateDiff = it.verticalScrollPixels / abs(it.verticalScrollPixels)
                viewModel.updateDate(dateDiff.toInt())
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                true
            }
            .focusRequester(focusRequester)
            .focusable(),
        timeText = { TimeText() }
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            state = scrollState,
            verticalArrangement = Arrangement.Top,
            autoCentering = null
        ) {
            item {
                ListHeader {
                    DateText(state.value.currentDate, onClick = { viewModel.resetDate() })
                }
            }
            if (state.value.loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .padding(20.dp),
                        indicatorColor = Color.White.copy(alpha = 0.8f),
                        trackColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                        strokeWidth = 2.dp,
                    )
                }
            } else {
                if (state.value.hourlyEvents != null) {
                    val events = state.value.hourlyEvents!!
                    item {
                        TideLevelChart(events = events, currentDate = state.value.currentDate)
                    }
                }
                item {
                    TidalExtremes(events = state.value.extremes)
                }
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        LifecycleResumeEffect(true) {
            viewModel.onResume()

            onPauseOrDispose {}
        }
    }
}

