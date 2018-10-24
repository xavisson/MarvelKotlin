package com.xavisson.marvel.domain.character

import io.reactivex.Observable

class CharacterResource(
        private val characterApi: CharacterApi
) {

    var currentCharacterList: List<CharacterItem> = emptyList()

    fun searchForCharacters(characterName: String): Observable<List<CharacterItem>> {
        return characterApi.searchForCharacter(characterName)
                .doOnNext { currentCharacterList = it }
    }


}