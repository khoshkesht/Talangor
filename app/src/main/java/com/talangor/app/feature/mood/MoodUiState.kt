package com.talangor.app.feature.mood

import com.talangor.app.core.model.MoodOption
import com.talangor.app.domain.model.ActionSuggestion
import com.talangor.app.domain.model.MoodEntry

data class MoodUiState(
    val moods: List<MoodOption> = MoodOption.entries,
    val selectedMood: MoodOption? = null,
    val suggestions: List<ActionSuggestion> = emptyList(),
    val history: List<MoodEntry> = emptyList()
)
