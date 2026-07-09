package com.talangor.app.data.local.mapper

import com.talangor.app.data.local.entity.MoodEntryEntity
import com.talangor.app.domain.model.MoodEntry

fun MoodEntryEntity.toDomain(): MoodEntry {
    return MoodEntry(
        id = id,
        moodLabel = moodLabel,
        actionTitle = actionTitle,
        actionDescription = actionDescription,
        createdAt = createdAt
    )
}
