package com.talangor.app.feature.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.talangor.app.domain.usecase.CompleteActionUseCase
import com.talangor.app.domain.usecase.GetActionForMoodUseCase
import com.talangor.app.domain.usecase.GetHistoryUseCase
import com.talangor.app.domain.usecase.SkipActionUseCase
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

    init {
        viewModelScope.launch {
            getHistoryUseCase().collect { entries ->
                _uiState.update { it.copy(history = entries) }
            }
        }
    }

    fun selectMood(mood: MoodChoice) {
        _uiState.update {
            it.copy(
                selectedMood = mood,
                selectedEnergy = null,
                suggestedAction = null,
                errorMessage = null
            )
        }
    }

    fun selectEnergy(energy: EnergyChoice) {
        val mood = _uiState.value.selectedMood ?: return

        _uiState.update {
            it.copy(
                selectedEnergy = energy,
                suggestedAction = null,
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
        }
    }

    fun requestAnotherAction() {
        val energy = _uiState.value.selectedEnergy ?: return
        selectEnergy(energy)
    }

    fun skipAndRequestAnother() {
        val logId = _uiState.value.suggestedAction?.logId
        val mood = _uiState.value.selectedMood ?: return
        val energy = _uiState.value.selectedEnergy ?: return

        _uiState.update {
            it.copy(
                suggestedAction = null,
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
        }
    }

    fun completeAction(helped: Boolean) {
        val logId = _uiState.value.suggestedAction?.logId ?: return

        viewModelScope.launch {
            completeActionUseCase(logId = logId, helped = helped)
        }
    }

    fun skipAction() {
        val logId = _uiState.value.suggestedAction?.logId ?: return

        viewModelScope.launch {
            skipActionUseCase(logId = logId)
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
