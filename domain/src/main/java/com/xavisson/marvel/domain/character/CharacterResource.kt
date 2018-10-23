package com.xavisson.marvel.domain.character

import io.reactivex.Observable

class CharacterResource {

    fun searchForCharacters(characterName: String): Observable<List<CharacterItem>> {
        return Observable.just(listOf(CharacterItem(
                id = 2,
                name = "Name",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0.jpg"
        )))
    }
}