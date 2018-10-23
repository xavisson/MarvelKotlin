package com.xavisson.marvel.domain.character

data class CharacterItem(
        val id: Int,
        val name: String,
        val imageUrl: String?
)

data class SearchCharacterItem(
        val characterName: String?,
        val items: List<CharacterItem>
)