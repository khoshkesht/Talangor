package com.talangor.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.talangor.app.data.local.model.MoodLogWithActionEntity
import com.talangor.app.data.local.entity.MoodLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodLogDao {
    @Query("SELECT * FROM mood_logs ORDER BY createdAt DESC")
    fun observeLogs(): Flow<List<MoodLogEntity>>

    @Query("SELECT * FROM mood_logs WHERE mood = :mood ORDER BY createdAt DESC")
    fun observeLogsByMood(mood: String): Flow<List<MoodLogEntity>>

    @Query(
        """
        SELECT mood_logs.id AS id,
            mood_logs.mood AS mood,
            mood_logs.energyLevel AS energyLevel,
            mood_logs.selectedActionId AS selectedActionId,
            actions.title AS actionTitle,
            actions.description AS actionDescription,
            mood_logs.wasCompleted AS wasCompleted,
            mood_logs.helped AS helped,
            mood_logs.createdAt AS createdAt
        FROM mood_logs
        LEFT JOIN actions ON actions.id = mood_logs.selectedActionId
        ORDER BY mood_logs.createdAt DESC
        """
    )
    fun observeHistory(): Flow<List<MoodLogWithActionEntity>>

    @Query("SELECT * FROM mood_logs WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): MoodLogEntity?

    @Query("SELECT * FROM mood_logs WHERE mood = :mood AND energyLevel = :energyLevel ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLastSuggestion(mood: String, energyLevel: String): MoodLogEntity?

    @Query(
        """
        SELECT selectedActionId
        FROM mood_logs
        WHERE mood = :mood
            AND energyLevel = :energyLevel
            AND helped = 1
            AND selectedActionId IS NOT NULL
        GROUP BY selectedActionId
        ORDER BY COUNT(*) DESC, MAX(createdAt) DESC
        """
    )
    suspend fun getHelpedActionIds(mood: String, energyLevel: String): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: MoodLogEntity): Long

    @Update
    suspend fun update(log: MoodLogEntity)

    @Query("UPDATE mood_logs SET wasCompleted = :wasCompleted, helped = :helped WHERE id = :id")
    suspend fun updateResult(id: Long, wasCompleted: Boolean, helped: Boolean)
}
