package com.calbert.blackkitegauntlet.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calbert.blackkitegauntlet.presentation.data.AppContainer
import com.calbert.blackkitegauntlet.presentation.data.TidalEvent
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

class MainViewModel(private val container: AppContainer) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    private val dateLimits = getCurrentYearBounds()

    fun updateDate(change: Int) {
        var dateString = ""
        _state.update { s ->
            val nextDate = s.currentDate + change.toLong()
            dateString = LocalDate.ofEpochDay(nextDate).toString()
            val clampedValue = max(dateLimits.first, min(nextDate, dateLimits.second))
            s.copy(currentDate = clampedValue)
        }
        getEvent(dateString)
    }

    fun resetDate() {
        val nextState = MainUiState()
        val dateString = LocalDate.ofEpochDay(nextState.currentDate).toString()
        _state.update { nextState }
        getEvent(dateString)
    }

    fun state(): StateFlow<MainUiState> {
        Log.i("UI State", "Loading entry for date")
        val uiState = _state.asStateFlow()
        if (uiState.value.extremes != null) {
            return uiState
        }

        viewModelScope.launch {
            val dateString = LocalDate.ofEpochDay(uiState.value.currentDate).toString()
            val list = container.eventRepository.getExtremesStream(dateString).first()
            _state.update { s ->
                s.copy(extremes = list)
            }
        }

        return uiState
    }

    private var getEventJob: Job? = null

    private fun getEvent(date: String) {
        if (_state.value.extremes != null) {
            _state.update { s -> s.copy(extremes = null) }
        }

        getEventJob?.cancel()
        getEventJob = viewModelScope.launch {
            delay(200)
            val list = container.eventRepository.getExtremesStream(date).first()
            _state.update { s ->
                s.copy(extremes = list)
            }
        }
    }
}

data class MainUiState(
    val currentDate: Long = LocalDate.now().toEpochDay(),
    val extremes: List<TidalEvent>? = null
)
