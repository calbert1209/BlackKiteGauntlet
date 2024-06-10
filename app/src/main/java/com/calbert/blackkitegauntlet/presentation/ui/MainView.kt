package com.calbert.blackkitegauntlet.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import kotlin.math.abs


@Composable
fun MainView(
    viewModel:  MainViewModel = viewModel(),
) {
    val scrollState = rememberScalingLazyListState(initialCenterItemScrollOffset = 40)
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current
    val state = viewModel.uiState.collectAsState()

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
            item {
                DateAdjust(
                    onChange = { viewModel.updateDate(it) },
                    onReset = { viewModel.resetDate() }
                )
            }
            item { DateText(state.value.currentDate) }
            item { Text("foo") }
            item { Text("bar") }
            item { Text("baz") }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

