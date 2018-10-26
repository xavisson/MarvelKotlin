package com.xavisson.marvel.domain.comic

import io.reactivex.Observable

class ComicResource(
        private val comicApi: ComicApi
) {

    fun getComicsFromCharacterId(characterId: Int): Observable<List<ComicItem>> {
        return comicApi.getComicsFromCharacterId(characterId)
    }
}