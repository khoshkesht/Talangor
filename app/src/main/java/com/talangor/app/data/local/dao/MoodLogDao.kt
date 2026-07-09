package com.talangor.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.talangor.app.data.local.entity.MoodLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodLogDao {
    @Query("SELECT * FROM mood_logs ORDER BY createdAt DESC")
    fun observeLogs(): Flow<List<MoodLogEntity>>

    @Query("SELECT * FROM mood_logs WHERE mood = :mood ORDER BY createdAt DESC")
    fun observeLogsByMood(mood: String): Flow<List<MoodLogEntity>>

    @Query("SELECT * FROM mood_logs WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): MoodLogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: MoodLogEntity): Long

    @Update
    suspend fun update(log: MoodLogEntity)

    @Query("UPDATE mood_logs SET wasCompleted = :wasCompleted, helped = :helped WHERE id = :id")
    suspend fun updateResult(id: Long, wasCompleted: Boolean, helped: Boolean)
}
