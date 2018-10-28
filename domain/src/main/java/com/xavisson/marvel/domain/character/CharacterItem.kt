package com.xavisson.marvel.domain.character

import com.xavisson.marvel.domain.repository.Identifiable

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

data class SearchCharactersItems(
        val query: String,
        val items: List<CharacterItem>
): Identifiable<String> {
    override val key: String
        get() = query
}

