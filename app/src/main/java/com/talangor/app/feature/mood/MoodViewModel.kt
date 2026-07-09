package com.talangor.app.feature.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.talangor.app.domain.usecase.CompleteActionUseCase
import com.talangor.app.domain.usecase.GetActionForMoodUseCase
import com.talangor.app.domain.usecase.GetHistoryUseCase
import com.talangor.app.domain.usecase.SkipActionUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoodViewModel(
    private val getActionForMoodUseCase: GetActionForMoodUseCase,
    private val completeActionUseCase: CompleteActionUseCase,
    private val skipActionUseCase: SkipActionUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState: StateFlow<MoodUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            getHistoryUseCase().collect { entries ->
                _uiState.update { it.copy(history = entries) }
            }
        }
    }

    fun selectMood(mood: MoodChoice) {
        stopTimer()
        _uiState.update {
            it.copy(
                selectedMood = mood,
                selectedEnergy = null,
                suggestedAction = null,
                timerRemainingSeconds = 0,
                timerTotalSeconds = 0,
                errorMessage = null
            )
        }
    }

    fun selectEnergy(energy: EnergyChoice) {
        val mood = _uiState.value.selectedMood ?: return
        stopTimer()

        _uiState.update {
            it.copy(
                selectedEnergy = energy,
                suggestedAction = null,
                timerRemainingSeconds = 0,
                timerTotalSeconds = 0,
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val suggestion = getActionForMoodUseCase(
                mood = mood.key,
                energyLevel = energy.key
            )

            _uiState.update {
                it.copy(
                    suggestedAction = suggestion,
                    isLoading = false,
                    errorMessage = if (suggestion == null) "فعلا پیشنهادی برای این وضعیت پیدا نشد." else null
                )
            }

            suggestion?.action?.durationMinutes?.let { durationMinutes ->
                startTimer(durationMinutes)
            }
        }
    }

    fun skipAndRequestAnother() {
        val logId = _uiState.value.suggestedAction?.logId
        val mood = _uiState.value.selectedMood ?: return
        val energy = _uiState.value.selectedEnergy ?: return
        stopTimer()

        _uiState.update {
            it.copy(
                suggestedAction = null,
                timerRemainingSeconds = 0,
                timerTotalSeconds = 0,
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            if (logId != null) {
                skipActionUseCase(logId)
            }

            val suggestion = getActionForMoodUseCase(
                mood = mood.key,
                energyLevel = energy.key
            )

            _uiState.update {
                it.copy(
                    suggestedAction = suggestion,
                    isLoading = false,
                    errorMessage = if (suggestion == null) "فعلا پیشنهاد دیگری برای این وضعیت پیدا نشد." else null
                )
            }

            suggestion?.action?.durationMinutes?.let { durationMinutes ->
                startTimer(durationMinutes)
            }
        }
    }

    fun completeAction(helped: Boolean) {
        val logId = _uiState.value.suggestedAction?.logId ?: return
        stopTimer()

        viewModelScope.launch {
            completeActionUseCase(logId = logId, helped = helped)
        }
    }

    fun skipAction() {
        val logId = _uiState.value.suggestedAction?.logId ?: return
        stopTimer()

        viewModelScope.launch {
            skipActionUseCase(logId = logId)
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update { it.copy(isTimerRunning = false) }
    }

    private fun startTimer(durationMinutes: Int) {
        val totalSeconds = durationMinutes.coerceAtLeast(1) * 60
        timerJob?.cancel()
        _uiState.update {
            it.copy(
                timerRemainingSeconds = totalSeconds,
                timerTotalSeconds = totalSeconds,
                isTimerRunning = true
            )
        }

        timerJob = viewModelScope.launch {
            while (_uiState.value.timerRemainingSeconds > 0) {
                delay(1_000)
                _uiState.update { current ->
                    val nextRemaining = (current.timerRemainingSeconds - 1).coerceAtLeast(0)
                    current.copy(
                        timerRemainingSeconds = nextRemaining,
                        isTimerRunning = nextRemaining > 0
                    )
                }
            }
        }
    }

    class Factory(
        private val getActionForMoodUseCase: GetActionForMoodUseCase,
        private val completeActionUseCase: CompleteActionUseCase,
        private val skipActionUseCase: SkipActionUseCase,
        private val getHistoryUseCase: GetHistoryUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoodViewModel(
                getActionForMoodUseCase = getActionForMoodUseCase,
                completeActionUseCase = completeActionUseCase,
                skipActionUseCase = skipActionUseCase,
                getHistoryUseCase = getHistoryUseCase
            ) as T
        }
    }
}
