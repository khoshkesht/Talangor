package com.talangor.app.feature.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.talangor.app.core.model.MoodOption
import com.talangor.app.domain.model.ActionSuggestion
import com.talangor.app.domain.repository.MoodRepository
import com.talangor.app.domain.usecase.GetMoodActionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoodViewModel(
    private val repository: MoodRepository,
    private val getMoodActionsUseCase: GetMoodActionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState: StateFlow<MoodUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeEntries().collect { entries ->
                _uiState.update { it.copy(history = entries) }
            }
        }
    }

    fun selectMood(mood: MoodOption) {
        _uiState.update {
            it.copy(
                selectedMood = mood,
                suggestions = getMoodActionsUseCase(mood)
            )
        }
    }

    fun saveAction(action: ActionSuggestion) {
        val mood = _uiState.value.selectedMood ?: return

        viewModelScope.launch {
            repository.saveAction(
                moodLabel = mood.label,
                action = action
            )
        }
    }

    class Factory(
        private val repository: MoodRepository,
        private val getMoodActionsUseCase: GetMoodActionsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoodViewModel(
                repository = repository,
                getMoodActionsUseCase = getMoodActionsUseCase
            ) as T
        }
    }
}
