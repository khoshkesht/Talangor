package com.talangor.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.talangor.app.data.local.TalangorDatabase
import com.talangor.app.data.repository.ActionRepositoryImpl
import com.talangor.app.domain.usecase.CompleteActionUseCase
import com.talangor.app.domain.usecase.GetActionForMoodUseCase
import com.talangor.app.domain.usecase.GetHistoryUseCase
import com.talangor.app.domain.usecase.SkipActionUseCase
import com.talangor.app.feature.mood.MoodViewModel
import com.talangor.app.navigation.TalangorNavHost
import com.talangor.app.ui.theme.TalangorTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MoodViewModel by viewModels {
        val database = TalangorDatabase.getInstance(applicationContext)
        val actionRepository = ActionRepositoryImpl(
            actionDao = database.actionDao(),
            moodLogDao = database.moodLogDao()
        )

        MoodViewModel.Factory(
            getActionForMoodUseCase = GetActionForMoodUseCase(actionRepository),
            completeActionUseCase = CompleteActionUseCase(actionRepository),
            skipActionUseCase = SkipActionUseCase(actionRepository),
            getHistoryUseCase = GetHistoryUseCase(actionRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TalangorTheme {
                TalangorNavHost(viewModel = viewModel)
            }
        }
    }
}
