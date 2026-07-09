package com.talangor.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actions")
data class ActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val mood: String,
    val energyLevel: Int,
    val durationMinutes: Int,
    val category: String,
    val isActive: Boolean = true
)
