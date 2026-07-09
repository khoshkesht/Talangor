package com.talangor.app.domain.model

data class MoodEntry(
    val id: Long,
    val moodLabel: String,
    val actionTitle: String,
    val actionDescription: String,
    val createdAt: Long
)
