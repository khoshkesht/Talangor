package com.talangor.app.domain.repository

import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.model.SuggestedAction
import kotlinx.coroutines.flow.Flow

interface ActionRepository {
    suspend fun getActionForMood(mood: String, energyLevel: String): SuggestedAction?

    suspend fun completeAction(logId: Long, helped: Boolean)

    suspend fun skipAction(logId: Long)

    fun observeHistory(): Flow<List<ActionHistoryItem>>
}
