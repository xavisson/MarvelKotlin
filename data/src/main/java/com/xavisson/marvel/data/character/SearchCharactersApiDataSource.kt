package com.xavisson.marvel.data.character

import com.xavisson.marvel.domain.character.CharacterApi
import com.xavisson.marvel.domain.character.SearchCharactersItems
import com.xavisson.marvel.domain.repository.RxReadableDataSource
import io.reactivex.Observable

class SearchCharactersApiDataSource(
        private val characterApi: CharacterApi
) : RxReadableDataSource<String, SearchCharactersItems> {

    override fun getByKey(key: String): Observable<SearchCharactersItems> =
            characterApi.searchForCharacter(key)

    override fun getAll(): Observable<List<SearchCharactersItems>> =
            Observable.empty()
}