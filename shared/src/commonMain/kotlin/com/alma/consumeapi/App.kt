package com.alma.consumeapi

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alma.consumeapi.navigation.BottomBarItem
import com.alma.consumeapi.navigation.Screen
import com.alma.consumeapi.screens.*
import com.alma.consumeapi.viewmodel.RickAndMortyViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val viewModel: RickAndMortyViewModel = viewModel { RickAndMortyViewModel() }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val bottomBarScreens = listOf(
            Screen.Characters.route,
            Screen.Locations.route,
            Screen.Episodes.route
        )

        Scaffold(
            bottomBar = {
                if (currentRoute in bottomBarScreens) {
                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Characters.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    ) + fadeIn(animationSpec = tween(400))
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    ) + fadeOut(animationSpec = tween(400))
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(400)
                    ) + fadeIn(animationSpec = tween(400))
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(400)
                    ) + fadeOut(animationSpec = tween(400))
                }
            ) {
                composable(Screen.Characters.route) {
                    CharactersScreen(viewModel) { characterId ->
                        navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                    }
                }
                composable(Screen.Locations.route) {
                    LocationsScreen(viewModel) { locationId ->
                        navController.navigate(Screen.LocationDetail.createRoute(locationId))
                    }
                }
                composable(Screen.Episodes.route) {
                    EpisodesScreen(viewModel) { episodeId ->
                        navController.navigate(Screen.EpisodeDetail.createRoute(episodeId))
                    }
                }
                composable(
                    route = Screen.CharacterDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.savedStateHandle.get<Int>("id") ?: 0
                    CharacterDetailScreen(id, viewModel) { navController.popBackStack() }
                }
                composable(
                    route = Screen.LocationDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.savedStateHandle.get<Int>("id") ?: 0
                    LocationDetailScreen(id, viewModel) { navController.popBackStack() }
                }
                composable(
                    route = Screen.EpisodeDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.savedStateHandle.get<Int>("id") ?: 0
                    EpisodeDetailScreen(id, viewModel) { navController.popBackStack() }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomBarItem(Screen.Characters, "Characters", Icons.Default.Home),
        BottomBarItem(Screen.Locations, "Locations", Icons.Default.Place),
        BottomBarItem(Screen.Episodes, "Episodes", Icons.AutoMirrored.Filled.List)
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationRoute!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
