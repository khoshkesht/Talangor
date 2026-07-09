package com.talangor.app.data.repository

import com.talangor.app.data.local.dao.ActionDao
import com.talangor.app.data.local.dao.MoodLogDao
import com.talangor.app.data.local.entity.ActionEntity
import com.talangor.app.data.local.entity.MoodLogEntity
import com.talangor.app.data.local.mapper.toDomain
import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.model.SuggestedAction
import com.talangor.app.domain.repository.ActionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActionRepositoryImpl(
    private val actionDao: ActionDao,
    private val moodLogDao: MoodLogDao
) : ActionRepository {
    override suspend fun getActionForMood(mood: String, energyLevel: String): SuggestedAction? {
        val actions = actionDao.getActiveActionsForMood(mood = mood, energyLevel = energyLevel)
        if (actions.isEmpty()) {
            return null
        }

        val lastSuggestedActionId = moodLogDao
            .getLastSuggestion(mood = mood, energyLevel = energyLevel)
            ?.selectedActionId

        val candidates = actions.withoutLastSuggestion(lastSuggestedActionId)
        val helpedActionIds = moodLogDao.getHelpedActionIds(mood = mood, energyLevel = energyLevel)
        val selectedAction = candidates.preferPreviouslyHelpful(helpedActionIds)

        val logId = moodLogDao.insert(
            MoodLogEntity(
                mood = mood,
                energyLevel = energyLevel,
                selectedActionId = selectedAction.id,
                createdAt = System.currentTimeMillis()
            )
        )

        return SuggestedAction(
            logId = logId,
            action = selectedAction.toDomain()
        )
    }

    override suspend fun completeAction(logId: Long, helped: Boolean) {
        moodLogDao.updateResult(
            id = logId,
            wasCompleted = true,
            helped = helped
        )
    }

    override suspend fun skipAction(logId: Long) {
        moodLogDao.updateResult(
            id = logId,
            wasCompleted = false,
            helped = false
        )
    }

    override fun observeHistory(): Flow<List<ActionHistoryItem>> {
        return moodLogDao.observeHistory().map { history ->
            history.map { it.toDomain() }
        }
    }

    private fun List<ActionEntity>.withoutLastSuggestion(lastSuggestedActionId: Long?): List<ActionEntity> {
        if (size == 1 || lastSuggestedActionId == null) {
            return this
        }

        return filterNot { action -> action.id == lastSuggestedActionId }
    }

    private fun List<ActionEntity>.preferPreviouslyHelpful(helpedActionIds: List<Long>): ActionEntity {
        val helpedRank = helpedActionIds.withIndex().associate { (index, actionId) ->
            actionId to index
        }

        return minWithOrNull(
            compareBy<ActionEntity> { action -> helpedRank[action.id] ?: Int.MAX_VALUE }
                .thenBy { action -> action.durationMinutes }
                .thenBy { action -> action.id }
        ) ?: first()
    }
}
