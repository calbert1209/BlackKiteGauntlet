package com.calbert.blackkitegauntlet.presentation.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

class MainViewModel: ViewModel() {
    private val _state = MutableStateFlow(MainUiState())

    val uiState: StateFlow<MainUiState> = _state.asStateFlow()
    val dateLimits = getCurrentYearBounds()

    fun updateDate(change: Int) {
        _state.update { s ->
            val nextDate = s.currentDate + change.toLong()
            val clampedValue = max(dateLimits.first, min(nextDate, dateLimits.second))
            s.copy(currentDate = clampedValue)
        }
    }

    fun resetDate() {
        _state.update { MainUiState() }
    }
}

data class MainUiState(
    val currentDate: Long = LocalDate.now().toEpochDay()
)
