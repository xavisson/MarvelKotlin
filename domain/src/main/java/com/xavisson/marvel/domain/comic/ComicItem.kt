package com.xavisson.marvel.domain.comic

import com.xavisson.marvel.domain.repository.Identifiable

data class ComicItem(
        val id: Int?,
        val title: String,
        val imageUrl: String
)

data class SearchComicItems(
        val query: String,
        val items: List<ComicItem>
): Identifiable<String> {
    override val key: String
        get() = query
}