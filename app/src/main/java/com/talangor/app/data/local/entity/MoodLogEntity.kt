package com.talangor.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mood_logs",
    foreignKeys = [
        ForeignKey(
            entity = ActionEntity::class,
            parentColumns = ["id"],
            childColumns = ["selectedActionId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["createdAt"]),
        Index(value = ["mood", "energyLevel"]),
        Index(value = ["selectedActionId"])
    ]
)
data class MoodLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mood: String,
    val energyLevel: String,
    val selectedActionId: Long?,
    val wasCompleted: Boolean = false,
    val helped: Boolean = false,
    val createdAt: Long
)
