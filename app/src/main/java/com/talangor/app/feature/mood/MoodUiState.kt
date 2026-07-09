package com.talangor.app.feature.mood

import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.model.SuggestedAction

data class MoodChoice(
    val key: String,
    val label: String,
    val description: String
)

data class EnergyChoice(
    val key: String,
    val label: String,
    val description: String
)

data class MoodUiState(
    val moods: List<MoodChoice> = defaultMoodChoices,
    val energyLevels: List<EnergyChoice> = defaultEnergyChoices,
    val selectedMood: MoodChoice? = null,
    val selectedEnergy: EnergyChoice? = null,
    val suggestedAction: SuggestedAction? = null,
    val history: List<ActionHistoryItem> = emptyList(),
    val timerRemainingSeconds: Int = 0,
    val timerTotalSeconds: Int = 0,
    val isTimerRunning: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

private val defaultMoodChoices = listOf(
    MoodChoice("anxious", "مضطرب", "ذهنم درگیر و ناآرام است"),
    MoodChoice("bored", "بی‌حوصله", "حس می‌کنم هیچ چیز جذاب نیست"),
    MoodChoice("tired", "خسته", "انرژی جسمی یا ذهنی کمی دارم"),
    MoodChoice("unfocused", "بی‌تمرکز", "تمرکز کردن سخت شده"),
    MoodChoice("unmotivated", "بی‌انگیزه", "شروع کردن برایم سنگین است")
)

private val defaultEnergyChoices = listOf(
    EnergyChoice("low", "کم", "فقط توان یک قدم خیلی کوچک را دارم"),
    EnergyChoice("medium", "متوسط", "می‌توانم چند دقیقه همراهی کنم"),
    EnergyChoice("high", "زیاد", "آماده‌ام کمی فعال‌تر شروع کنم")
)
