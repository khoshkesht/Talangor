package com.talangor.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.talangor.app.data.local.entity.ActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions WHERE isActive = 1 ORDER BY mood ASC, energyLevel ASC, durationMinutes ASC")
    fun observeActiveActions(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE mood = :mood AND energyLevel = :energyLevel AND isActive = 1 ORDER BY durationMinutes ASC")
    fun observeActionsForMood(mood: String, energyLevel: String): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ActionEntity?

    @Query("SELECT COUNT(*) FROM actions")
    suspend fun countActions(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(action: ActionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actions: List<ActionEntity>)

    @Update
    suspend fun update(action: ActionEntity)

    @Query("UPDATE actions SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: Long)
}
