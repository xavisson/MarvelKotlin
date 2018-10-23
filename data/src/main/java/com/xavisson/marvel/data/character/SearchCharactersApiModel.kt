package com.xavisson.marvel.data.character

import com.google.gson.annotations.SerializedName
import com.xavisson.marvel.domain.character.CharacterItem
import com.xavisson.marvel.domain.character.SearchCharacterItem

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
        @SerializedName("thumbnail") val thumbnail: CharacterImage?
)

data class CharacterImage(
        @SerializedName("path") val path: String?,
        @SerializedName("extension") val extension: String?
)

fun SearchCharactersApiModel.toDomain(): List<CharacterItem> {
    return data?.results?.map { it.toDomain() } ?: emptyList()
}

fun CharacterApiModel.toDomain(): CharacterItem {
    return CharacterItem(
            id = id!!,
            name = name ?: "Unknown",
            imageUrl = thumbnail?.path + thumbnail?.extension
    )
}