package com.xavisson.marvel.domain.character

import io.reactivex.Observable

class CharacterResource(
        private val characterApi: CharacterApi
) {

    fun searchForCharacters(characterName: String): Observable<List<CharacterItem>> {
        return characterApi.searchForCharacter(characterName)
    }
}