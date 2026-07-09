package com.talangor.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val moodLabel: String,
    val actionTitle: String,
    val actionDescription: String,
    val createdAt: Long
)
