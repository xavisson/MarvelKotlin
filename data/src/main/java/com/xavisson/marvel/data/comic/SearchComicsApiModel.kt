package com.xavisson.marvel.data.comic

import com.google.gson.annotations.SerializedName
import com.xavisson.marvel.domain.comic.ComicItem
import com.xavisson.marvel.domain.comic.SearchComicItems

data class SearchComicsApiModel(
        @SerializedName("data") val data: ComicWrapperApiModel?
)

data class ComicWrapperApiModel(
        @SerializedName("offset") val offset: Int?,
        @SerializedName("limit") val limit: Int?,
        @SerializedName("total") val total: Int?,
        @SerializedName("count") val count: Int?,
        @SerializedName("results") val results: List<ComicApiModel>?
)

data class ComicApiModel(
        @SerializedName("id") val id: Int?,
        @SerializedName("title") val name: String?,
        @SerializedName("thumbnail") val thumbnail: ComicImage?
)

data class ComicImage(
        @SerializedName("path") val path: String?,
        @SerializedName("extension") val extension: String?
)

fun SearchComicsApiModel.toDomain(query: String): SearchComicItems {
    return SearchComicItems(
            query = query,
            items = data?.results?.map { it.toDomain() } ?: emptyList()
    )
}

fun ComicApiModel.toDomain(): ComicItem {
    return ComicItem(
            id = id!!,
            title = name ?: "Unknown title",
            imageUrl = thumbnail?.path + "." + thumbnail?.extension
    )
}