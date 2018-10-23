package com.xavisson.marvel.domain.character

import io.reactivex.Observable

interface CharacterApi {
    fun searchForCharacter(searchText: String): Observable<List<CharacterItem>>
}