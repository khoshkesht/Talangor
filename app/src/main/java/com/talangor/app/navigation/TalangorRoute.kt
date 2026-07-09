package com.talangor.app.navigation

sealed class TalangorRoute(val route: String) {
    data object MoodSelection : TalangorRoute("mood-selection")
    data object EnergySelection : TalangorRoute("energy-selection")
    data object SuggestedAction : TalangorRoute("suggested-action")
    data object Result : TalangorRoute("result/{helped}") {
        fun createRoute(helped: Boolean): String = "result/$helped"
    }
    data object History : TalangorRoute("history")
}
