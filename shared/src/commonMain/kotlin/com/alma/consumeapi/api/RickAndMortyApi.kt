package com.alma.consumeapi.api

import com.alma.consumeapi.models.CharacterResponse
import com.alma.consumeapi.models.EpisodeResponse
import com.alma.consumeapi.models.LocationResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class RickAndMortyApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getCharacters(page: Int = 1, name: String? = null): CharacterResponse {
        return client.get("https://rickandmortyapi.com/api/character") {
            parameter("page", page)
            if (name != null) parameter("name", name)
        }.body()
    }

    suspend fun getLocations(page: Int = 1): LocationResponse {
        return client.get("https://rickandmortyapi.com/api/location") {
            parameter("page", page)
        }.body()
    }

    suspend fun getEpisodes(page: Int = 1): EpisodeResponse {
        return client.get("https://rickandmortyapi.com/api/episode") {
            parameter("page", page)
        }.body()
    }
}
