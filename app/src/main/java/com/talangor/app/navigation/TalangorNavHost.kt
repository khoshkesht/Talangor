package com.talangor.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talangor.app.feature.mood.EnergySelectionScreen
import com.talangor.app.feature.mood.HistoryScreen
import com.talangor.app.feature.mood.MoodSelectionScreen
import com.talangor.app.feature.mood.MoodViewModel
import com.talangor.app.feature.mood.ResultScreen
import com.talangor.app.feature.mood.SuggestedActionScreen

@Composable
fun TalangorNavHost(viewModel: MoodViewModel) {
    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = TalangorRoute.MoodSelection.route
    ) {
        composable(TalangorRoute.MoodSelection.route) {
            MoodSelectionScreen(
                state = state,
                onMoodSelected = { mood ->
                    viewModel.selectMood(mood)
                    navController.navigate(TalangorRoute.EnergySelection.route)
                },
                onHistoryClick = {
                    navController.navigate(TalangorRoute.History.route)
                }
            )
        }

        composable(TalangorRoute.EnergySelection.route) {
            EnergySelectionScreen(
                state = state,
                onEnergySelected = { energy ->
                    viewModel.selectEnergy(energy)
                    navController.navigate(TalangorRoute.SuggestedAction.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(TalangorRoute.SuggestedAction.route) {
            SuggestedActionScreen(
                state = state,
                onCompleteClick = { helped ->
                    viewModel.completeAction(helped)
                    navController.navigate(TalangorRoute.Result.createRoute(helped))
                },
                onSkipClick = {
                    viewModel.skipAction()
                    navController.popBackStack(TalangorRoute.MoodSelection.route, inclusive = false)
                },
                onAnotherClick = viewModel::skipAndRequestAnother,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(TalangorRoute.Result.route) { backStackEntry ->
            val helped = backStackEntry.arguments?.getString("helped").toBoolean()

            ResultScreen(
                helped = helped,
                onNewMoodClick = {
                    navController.popBackStack(TalangorRoute.MoodSelection.route, inclusive = false)
                },
                onHistoryClick = {
                    navController.navigate(TalangorRoute.History.route)
                }
            )
        }

        composable(TalangorRoute.History.route) {
            HistoryScreen(
                history = state.history,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
