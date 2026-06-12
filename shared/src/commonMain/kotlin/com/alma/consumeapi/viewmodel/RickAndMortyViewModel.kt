package com.alma.consumeapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alma.consumeapi.api.RickAndMortyApi
import com.alma.consumeapi.models.Character
import com.alma.consumeapi.models.Episode
import com.alma.consumeapi.models.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RickAndMortyViewModel : ViewModel() {
    private val api = RickAndMortyApi()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations: StateFlow<List<Location>> = _locations

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadCharacters()
        loadLocations()
        loadEpisodes()
    }

    fun loadCharacters(name: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getCharacters(name = name)
                _characters.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadLocations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getLocations()
                _locations.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadEpisodes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getEpisodes()
                _episodes.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        loadCharacters(query)
    }
}
