package com.talangor.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talangor.app.feature.mood.MoodScreen
import com.talangor.app.feature.mood.MoodViewModel

@Composable
fun TalangorNavHost(viewModel: MoodViewModel) {
    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = TalangorRoute.Mood.route
    ) {
        composable(TalangorRoute.Mood.route) {
            MoodScreen(
                state = state,
                onMoodSelected = viewModel::selectMood,
                onSaveAction = viewModel::saveAction
            )
        }
    }
}
