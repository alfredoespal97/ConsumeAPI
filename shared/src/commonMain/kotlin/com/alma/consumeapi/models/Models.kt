package com.alma.consumeapi.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

@Serializable
data class LocationResponse(
    val info: Info,
    val results: List<Location>
)

@Serializable
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

@Serializable
data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String
)
