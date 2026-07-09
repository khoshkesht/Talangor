package com.talangor.app.navigation

sealed class TalangorRoute(val route: String) {
    data object MoodSelection : TalangorRoute("mood-selection")
    data object EnergySelection : TalangorRoute("energy-selection")
    data object SuggestedAction : TalangorRoute("suggested-action")
    data object Result : TalangorRoute("result/{completed}") {
        fun createRoute(completed: Boolean): String = "result/$completed"
    }
    data object History : TalangorRoute("history")
}
