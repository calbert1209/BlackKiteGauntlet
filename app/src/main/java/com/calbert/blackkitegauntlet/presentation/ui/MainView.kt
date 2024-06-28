package com.calbert.blackkitegauntlet.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import kotlin.math.abs


@Composable
fun MainView(
    viewModel:  MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val scrollState = rememberScalingLazyListState()
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current
    val state = viewModel.state().collectAsState()

    Scaffold (
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
            verticalArrangement = Arrangement.Top
        ) {
            item { DateText(state.value.currentDate, onClick = {viewModel.resetDate()}) }
            item {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
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

