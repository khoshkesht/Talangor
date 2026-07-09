package com.talangor.app.data.local.model

data class MoodLogWithActionEntity(
    val id: Long,
    val mood: String,
    val energyLevel: String,
    val selectedActionId: Long?,
    val actionTitle: String?,
    val actionDescription: String?,
    val wasCompleted: Boolean,
    val helped: Boolean,
    val createdAt: Long
)
