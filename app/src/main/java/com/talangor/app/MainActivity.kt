package com.talangor.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.talangor.app.data.local.TalangorDatabase
import com.talangor.app.data.repository.MoodRepositoryImpl
import com.talangor.app.domain.usecase.GetMoodActionsUseCase
import com.talangor.app.feature.mood.MoodViewModel
import com.talangor.app.navigation.TalangorNavHost
import com.talangor.app.ui.theme.TalangorTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MoodViewModel by viewModels {
        val database = TalangorDatabase.getInstance(applicationContext)
        val repository = MoodRepositoryImpl(database.moodEntryDao())

        MoodViewModel.Factory(
            repository = repository,
            getMoodActionsUseCase = GetMoodActionsUseCase()
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
