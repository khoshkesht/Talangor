package com.talangor.app.domain.usecase

import com.talangor.app.domain.repository.ActionRepository

class SkipActionUseCase(
    private val actionRepository: ActionRepository
) {
    suspend operator fun invoke(logId: Long) {
        actionRepository.skipAction(logId = logId)
    }
}
