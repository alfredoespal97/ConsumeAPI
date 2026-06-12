package com.alma.consumeapi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.alma.consumeapi.models.Character
import com.alma.consumeapi.models.Episode
import com.alma.consumeapi.models.Location
import com.alma.consumeapi.ui.CharacterSkeleton
import com.alma.consumeapi.viewmodel.RickAndMortyViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Characters : Screen("characters", "Characters", Icons.Default.Home)
    object Locations : Screen("locations", "Locations", Icons.Default.Place)
    object Episodes : Screen("episodes", "Episodes", Icons.AutoMirrored.Filled.List)
}

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val viewModel: RickAndMortyViewModel = viewModel { RickAndMortyViewModel() }

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Characters.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Characters.route) {
                    CharactersScreen(viewModel)
                }
                composable(Screen.Locations.route) {
                    LocationsScreen(viewModel)
                }
                composable(Screen.Episodes.route) {
                    EpisodesScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Characters, Screen.Locations, Screen.Episodes)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
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

@Composable
fun CharactersScreen(viewModel: RickAndMortyViewModel) {
    val characters by viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            placeholder = { Text("Search characters...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        if (isLoading && characters.isEmpty()) {
            LazyColumn {
                items(10) {
                    CharacterSkeleton()
                }
            }
        } else {
            LazyColumn {
                items(characters) { character ->
                    CharacterItem(character)
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${character.status} - ${character.species}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun LocationsScreen(viewModel: RickAndMortyViewModel) {
    val locations by viewModel.locations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading && locations.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(locations) { location ->
                LocationItem(location)
            }
        }
    }
}

@Composable
fun LocationItem(location: Location) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${location.type}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Dimension: ${location.dimension}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun EpisodesScreen(viewModel: RickAndMortyViewModel) {
    val episodes by viewModel.episodes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading && episodes.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(episodes) { episode ->
                EpisodeItem(episode)
            }
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = episode.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Air Date: ${episode.air_date}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Episode: ${episode.episode}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
