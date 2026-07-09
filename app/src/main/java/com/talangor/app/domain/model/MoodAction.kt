package com.talangor.app.domain.model

data class MoodAction(
    val id: Long,
    val title: String,
    val description: String,
    val mood: String,
    val energyLevel: String,
    val durationMinutes: Int,
    val category: String
)
