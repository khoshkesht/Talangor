package com.talangor.app.navigation

sealed class TalangorRoute(val route: String) {
    data object Mood : TalangorRoute("mood")
}
