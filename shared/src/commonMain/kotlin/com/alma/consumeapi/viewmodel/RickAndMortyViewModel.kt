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

    private val _characterDetail = MutableStateFlow<Character?>(null)
    val characterDetail: StateFlow<Character?> = _characterDetail

    private val _locationDetail = MutableStateFlow<Location?>(null)
    val locationDetail: StateFlow<Location?> = _locationDetail

    private val _episodeDetail = MutableStateFlow<Episode?>(null)
    val episodeDetail: StateFlow<Episode?> = _episodeDetail

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

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _characterDetail.value = null
            _isLoading.value = true
            try {
                _characterDetail.value = api.getCharacter(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getLocation(id: Int) {
        viewModelScope.launch {
            _locationDetail.value = null
            _isLoading.value = true
            try {
                _locationDetail.value = api.getLocation(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getEpisode(id: Int) {
        viewModelScope.launch {
            _episodeDetail.value = null
            _isLoading.value = true
            try {
                _episodeDetail.value = api.getEpisode(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
