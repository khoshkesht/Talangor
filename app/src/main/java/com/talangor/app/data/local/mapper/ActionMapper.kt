package com.talangor.app.data.local.mapper

import com.talangor.app.data.local.entity.ActionEntity
import com.talangor.app.data.local.model.MoodLogWithActionEntity
import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.model.MoodAction

fun ActionEntity.toDomain(): MoodAction {
    return MoodAction(
        id = id,
        title = title,
        description = description,
        mood = mood,
        energyLevel = energyLevel,
        durationMinutes = durationMinutes,
        category = category
    )
}

fun MoodLogWithActionEntity.toDomain(): ActionHistoryItem {
    return ActionHistoryItem(
        id = id,
        mood = mood,
        energyLevel = energyLevel,
        selectedActionId = selectedActionId,
        actionTitle = actionTitle,
        actionDescription = actionDescription,
        wasCompleted = wasCompleted,
        helped = helped,
        createdAt = createdAt
    )
}
