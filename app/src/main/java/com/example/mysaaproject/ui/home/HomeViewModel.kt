package com.example.mysaaproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.data.awards.AwardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

/** Remaining time to the event, split for the countdown display. */
data class Countdown(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    /** True while the event is still upcoming — drives the "Coming soon" label (TC_IOS_HOME_FUN_002). */
    val isBeforeEvent: Boolean,
)

/** State of the Awards section (TC_IOS_HOME_GUI_002/003/004). */
sealed interface AwardsState {
    data object Loading : AwardsState
    data class Success(val awards: List<Award>) : AwardsState
    data object Empty : AwardsState
    data object Error : AwardsState
}

/**
 * Drives the Home dashboard: a live countdown to the event date, the stateful Awards list,
 * and the mock feature flags (Kudos visibility, notification badge). Mirrors the Login
 * feature's AndroidViewModel pattern.
 */
class HomeViewModel : ViewModel() {

    private val awardsRepository = AwardsRepository()

    private val _awardsState = MutableStateFlow<AwardsState>(AwardsState.Loading)
    val awardsState: StateFlow<AwardsState> = _awardsState.asStateFlow()

    /** Mock flags — see clarifications: Kudos shown, badge visible. */
    val isKudosAvailable: Boolean = true
    val unreadNotifications: Int = 3

    /** Live countdown, recomputed every second toward [eventEpochMillis]. */
    val countdown: StateFlow<Countdown> =
        flow {
            while (true) {
                emit(computeCountdown(eventEpochMillis - System.currentTimeMillis()))
                kotlinx.coroutines.delay(1_000L)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = computeCountdown(eventEpochMillis - System.currentTimeMillis()),
        )

    init {
        loadAwards()
    }

    /** Loads awards, mapping the result to Loading → Success/Empty/Error. */
    fun loadAwards() {
        _awardsState.value = AwardsState.Loading
        viewModelScope.launch {
            awardsRepository.loadAwards()
                .onSuccess { awards ->
                    _awardsState.value =
                        if (awards.isEmpty()) AwardsState.Empty else AwardsState.Success(awards)
                }
                .onFailure { _awardsState.value = AwardsState.Error }
        }
    }

    /** Re-triggers the awards fetch after an error (TC_IOS_HOME_FUN_003). */
    fun retryAwards() = loadAwards()

    companion object {
        /** Event date 26/12/2025 00:00 (Asia/Ho_Chi_Minh). Calendar keeps this API 24-safe. */
        val eventEpochMillis: Long = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
            .apply {
                clear()
                set(2025, Calendar.DECEMBER, 26, 0, 0, 0)
            }
            .timeInMillis

        /**
         * Pure countdown computation (unit-testable). A non-positive remainder means the event
         * has passed → all zeros and [Countdown.isBeforeEvent] = false.
         */
        fun computeCountdown(remainingMillis: Long): Countdown {
            if (remainingMillis <= 0L) return Countdown(0, 0, 0, isBeforeEvent = false)
            val totalMinutes = remainingMillis / 60_000L
            val days = (totalMinutes / (60L * 24L)).toInt()
            val hours = ((totalMinutes / 60L) % 24L).toInt()
            val minutes = (totalMinutes % 60L).toInt()
            return Countdown(days, hours, minutes, isBeforeEvent = true)
        }
    }
}
