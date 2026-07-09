package com.talangor.app.domain.usecase

import com.talangor.app.domain.model.SuggestedAction
import com.talangor.app.domain.repository.ActionRepository

class GetActionForMoodUseCase(
    private val actionRepository: ActionRepository
) {
    suspend operator fun invoke(mood: String, energyLevel: String): SuggestedAction? {
        return actionRepository.getActionForMood(
            mood = mood,
            energyLevel = energyLevel
        )
    }
}
