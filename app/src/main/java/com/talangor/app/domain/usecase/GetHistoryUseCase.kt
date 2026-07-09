package com.talangor.app.domain.usecase

import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.repository.ActionRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCase(
    private val actionRepository: ActionRepository
) {
    operator fun invoke(): Flow<List<ActionHistoryItem>> {
        return actionRepository.observeHistory()
    }
}
