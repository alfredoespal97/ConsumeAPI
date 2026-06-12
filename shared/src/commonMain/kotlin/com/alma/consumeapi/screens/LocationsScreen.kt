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
import com.alma.consumeapi.models.Location
import com.alma.consumeapi.viewmodel.RickAndMortyViewModel

@Composable
fun LocationsScreen(viewModel: RickAndMortyViewModel, onLocationClick: (Int) -> Unit) {
    val locations by viewModel.locations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading && locations.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(locations) { location ->
                LocationItem(location) { onLocationClick(location.id) }
            }
        }
    }
}

@Composable
fun LocationItem(location: Location, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${location.type}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Dimension: ${location.dimension}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
