package com.xavisson.marvel.data.character

import com.google.gson.annotations.SerializedName
import com.xavisson.marvel.domain.character.CharacterItem
import com.xavisson.marvel.domain.character.CharacterItemComics
import com.xavisson.marvel.domain.character.SearchCharactersItems

data class SearchCharactersApiModel(
        @SerializedName("data") val data: DataApiModel?
)

data class DataApiModel(
        @SerializedName("offset") val offset: Int?,
        @SerializedName("limit") val limit: Int?,
        @SerializedName("total") val total: Int?,
        @SerializedName("count") val count: Int?,
        @SerializedName("results") val results: List<CharacterApiModel>?
)

data class CharacterApiModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("thumbnail") val thumbnail: CharacterImage?,
        @SerializedName("comics") val comics: CharacterComics?
)

data class CharacterImage(
        @SerializedName("path") val path: String?,
        @SerializedName("extension") val extension: String?
)

data class CharacterComics(
        @SerializedName("available") val available: Int?,
        @SerializedName("collectionURI") val collectionURI: String?
)

fun SearchCharactersApiModel.toDomain(query: String): SearchCharactersItems {
    return SearchCharactersItems(
            query = query,
            items = data?.results?.map { it.toDomain() } ?: kotlin.collections.emptyList()
    )
}

fun CharacterApiModel.toDomain(): CharacterItem {
    return CharacterItem(
            id = id!!,
            name = name ?: "Unknown",
            imageUrl = thumbnail?.path + "." + thumbnail?.extension,
            description = description,
            comics = comics?.toDomain()
    )
}

fun CharacterComics.toDomain(): CharacterItemComics {
    return CharacterItemComics(
            available = available,
            collectionURI = collectionURI
    )
}