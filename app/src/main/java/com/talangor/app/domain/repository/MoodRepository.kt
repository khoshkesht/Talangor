package com.talangor.app.domain.repository

import com.talangor.app.domain.model.ActionSuggestion
import com.talangor.app.domain.model.MoodEntry
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    fun observeEntries(): Flow<List<MoodEntry>>

    suspend fun saveAction(moodLabel: String, action: ActionSuggestion)
}
