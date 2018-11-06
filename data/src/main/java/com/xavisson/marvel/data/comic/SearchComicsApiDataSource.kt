package com.xavisson.marvel.data.comic

import com.xavisson.marvel.domain.comic.ComicApi
import com.xavisson.marvel.domain.comic.SearchComicItems
import com.xavisson.marvel.domain.repository.RxReadableDataSource
import io.reactivex.Observable

class SearchComicsApiDataSource(
        private val comicApi: ComicApi
) : RxReadableDataSource<String, SearchComicItems> {

    override fun getByKey(key: String): Observable<SearchComicItems> =
            comicApi.getComicsFromCharacterId(key.toInt())

    override fun getAll(): Observable<List<SearchComicItems>> =
            Observable.empty()
}