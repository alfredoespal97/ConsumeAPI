package com.alma.consumeapi.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Characters : Screen("characters")
    object Locations : Screen("locations")
    object Episodes : Screen("episodes")
    object CharacterDetail : Screen("character_detail/{id}") {
        fun createRoute(id: Int) = "character_detail/$id"
    }
    object LocationDetail : Screen("location_detail/{id}") {
        fun createRoute(id: Int) = "location_detail/$id"
    }
    object EpisodeDetail : Screen("episode_detail/{id}") {
        fun createRoute(id: Int) = "episode_detail/$id"
    }
}

data class BottomBarItem(val screen: Screen, val title: String, val icon: ImageVector)
