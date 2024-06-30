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
    private val eventCache = mutableMapOf<Long, SortedTidalEvents>()

    fun onResume() {
        resetDate()
    }

    fun updateDate(change: Int) {
        val nextDate = _state.value.currentDate + change.toLong()
        val clampedDate = max(dateLimits.first, min(nextDate, dateLimits.second))
        getEvent(clampedDate)
    }

    fun resetDate() {
        val nextDate = LocalDate.now().toEpochDay()
        val clampedDate = max(dateLimits.first, min(nextDate, dateLimits.second))
        getEvent(clampedDate)
    }

    fun state(): StateFlow<MainUiState> {
        Log.i("UI State", "Loading entry for date")
        val uiState = _state.asStateFlow()
        if (uiState.value.extremes == null) {
            getEvent(uiState.value.currentDate)
        }

        return uiState
    }

    private var getEventJob: Job? = null

    private fun getEvent(date: Long) {
        if (eventCache.containsKey(date)){
            val event = eventCache[date]!!
            _state.update { s -> s.copy(extremes = event.extremes, hourlyEvents = event.hourlyEvents, currentDate = date) }
            return
        }

        _state.update { s -> s.copy(extremes = null, currentDate = date, loading = true) }

        getEventJob?.cancel()
        getEventJob = viewModelScope.launch {
            delay(200)
            val dateString = LocalDate.ofEpochDay(date).toString()
            val events = container.eventRepository.getEventsStream(dateString).first()
            val extremes = events.filter { e -> e.type != "hourly" }
            val hourlyEvents = events.filter { e -> e.type == "hourly" }
            eventCache[date] = SortedTidalEvents(extremes = extremes, hourlyEvents = hourlyEvents)
            _state.update { s ->
                s.copy(extremes = extremes, loading = false, hourlyEvents = hourlyEvents)
            }
        }
    }
}

data class MainUiState(
    val loading: Boolean = true,
    val currentDate: Long = LocalDate.now().toEpochDay(),
    val extremes: List<TidalEvent>? = null,
    val hourlyEvents: List<TidalEvent>? = null
)

data class SortedTidalEvents (
    val extremes: List<TidalEvent>,
    val hourlyEvents: List<TidalEvent>
)
