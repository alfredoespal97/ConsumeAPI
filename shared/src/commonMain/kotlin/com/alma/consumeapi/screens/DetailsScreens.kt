package com.alma.consumeapi.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alma.consumeapi.viewmodel.RickAndMortyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(id: Int, viewModel: RickAndMortyViewModel, onBack: () -> Unit) {
    val character by viewModel.characterDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        viewModel.getCharacter(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character?.name ?: "Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            character?.let { char ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(elevation = CardDefaults.cardElevation(8.dp)) {
                        AsyncImage(
                            model = char.image,
                            contentDescription = char.name,
                            modifier = Modifier.size(300.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(text = char.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(8.dp))
                    DetailRow("Status", char.status)
                    DetailRow("Species", char.species)
                    DetailRow("Gender", char.gender)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(id: Int, viewModel: RickAndMortyViewModel, onBack: () -> Unit) {
    val location by viewModel.locationDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        viewModel.getLocation(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(location?.name ?: "Location Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            location?.let { loc ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
                ) {
                    Text(text = loc.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(16.dp))
                    DetailRow("Type", loc.type)
                    DetailRow("Dimension", loc.dimension)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(id: Int, viewModel: RickAndMortyViewModel, onBack: () -> Unit) {
    val episode by viewModel.episodeDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        viewModel.getEpisode(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(episode?.name ?: "Episode Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            episode?.let { ep ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
                ) {
                    Text(text = ep.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(16.dp))
                    DetailRow("Air Date", ep.air_date)
                    DetailRow("Episode", ep.episode)
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = "$label: ", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
