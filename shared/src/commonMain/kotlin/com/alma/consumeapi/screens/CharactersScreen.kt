package com.alma.consumeapi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alma.consumeapi.models.Character
import com.alma.consumeapi.ui.CharacterSkeleton
import com.alma.consumeapi.viewmodel.RickAndMortyViewModel

@Composable
fun CharactersScreen(viewModel: RickAndMortyViewModel, onCharacterClick: (Int) -> Unit) {
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
                    CharacterItem(character) { onCharacterClick(character.id) }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }
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
