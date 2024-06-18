package com.calbert.blackkitegauntlet.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calbert.blackkitegauntlet.presentation.data.AppContainer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.max
import kotlin.math.min

private fun getCurrentYearBounds(): Pair<Long, Long> {
    val now = LocalDate.now()
    val currentYear = now.year
    val start = LocalDate.of(currentYear, 1, 1)
    val end = LocalDate.of(currentYear, 12, 31)
    return Pair(start.toEpochDay(), end.toEpochDay())
}

class MainViewModel(private val container: AppContainer): ViewModel() {
    private val _state = MutableStateFlow(MainUiState())

    val uiState: StateFlow<MainUiState> = _state.asStateFlow()
    private val dateLimits = getCurrentYearBounds()

    fun updateDate(change: Int) {
        _state.update { s ->
            val nextDate = s.currentDate + change.toLong()
            val clampedValue = max(dateLimits.first, min(nextDate, dateLimits.second))
            s.copy(currentDate = clampedValue)
        }
        getEvent("2024-12-09T05:00:00+0900")
    }

    fun resetDate() {
        _state.update { MainUiState() }
        getEvent("2024-08-03T05:00:00+0900")
    }

    private var getEventJob: Job? = null

    private fun getEvent(timestamp: String) {
        getEventJob?.cancel()
        getEventJob = viewModelScope.launch {
            delay(200)
            val flow = container.eventRepository.getEventStream(timestamp)
            val first = flow.first()
            Log.i("db read", first.toString())
        }
    }
}

data class MainUiState(
    val currentDate: Long = LocalDate.now().toEpochDay()
)
