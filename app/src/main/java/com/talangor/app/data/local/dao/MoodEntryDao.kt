package com.talangor.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.talangor.app.data.local.entity.MoodEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodEntryDao {
    @Query("SELECT * FROM mood_entries ORDER BY createdAt DESC")
    fun observeEntries(): Flow<List<MoodEntryEntity>>

    @Insert
    suspend fun insert(entry: MoodEntryEntity)
}
