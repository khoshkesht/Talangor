package com.talangor.app.domain.usecase

import com.talangor.app.domain.repository.ActionRepository

class CompleteActionUseCase(
    private val actionRepository: ActionRepository
) {
    suspend operator fun invoke(logId: Long, helped: Boolean) {
        actionRepository.completeAction(logId = logId, helped = helped)
    }
}
