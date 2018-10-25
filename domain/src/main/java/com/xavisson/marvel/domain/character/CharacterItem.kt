package com.xavisson.marvel.domain.character

data class CharacterItem(
        val id: Int,
        val name: String,
        val imageUrl: String?,
        val description: String?,
        val comics: CharacterItemComics?
)

data class CharacterItemComics(
        val available: Int?,
        val collectionURI: String?
)

