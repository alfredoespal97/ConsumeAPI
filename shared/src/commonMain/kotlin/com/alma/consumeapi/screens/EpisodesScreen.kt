package com.alma.consumeapi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alma.consumeapi.models.Episode
import com.alma.consumeapi.viewmodel.RickAndMortyViewModel

@Composable
fun EpisodesScreen(viewModel: RickAndMortyViewModel, onEpisodeClick: (Int) -> Unit) {
    val episodes by viewModel.episodes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading && episodes.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(episodes) { episode ->
                EpisodeItem(episode) { onEpisodeClick(episode.id) }
            }
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = episode.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Air Date: ${episode.air_date}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Episode: ${episode.episode}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
