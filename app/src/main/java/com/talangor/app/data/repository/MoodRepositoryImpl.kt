package com.talangor.app.data.repository

import com.talangor.app.data.local.dao.MoodEntryDao
import com.talangor.app.data.local.entity.MoodEntryEntity
import com.talangor.app.data.local.mapper.toDomain
import com.talangor.app.domain.model.ActionSuggestion
import com.talangor.app.domain.model.MoodEntry
import com.talangor.app.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoodRepositoryImpl(
    private val moodEntryDao: MoodEntryDao
) : MoodRepository {
    override fun observeEntries(): Flow<List<MoodEntry>> {
        return moodEntryDao.observeEntries().map { entries ->
            entries.map { it.toDomain() }
        }
    }

    override suspend fun saveAction(moodLabel: String, action: ActionSuggestion) {
        moodEntryDao.insert(
            MoodEntryEntity(
                moodLabel = moodLabel,
                actionTitle = action.title,
                actionDescription = action.description,
                createdAt = System.currentTimeMillis()
            )
        )
    }
}
