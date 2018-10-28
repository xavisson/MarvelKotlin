package com.xavisson.marvel.domain.character

import com.xavisson.marvel.domain.repository.RxBaseRepository
import io.reactivex.Observable

class CharacterResource(
        private val searchCharactersRepository: RxBaseRepository<String, SearchCharactersItems>
) {

    var currentCharacterList: List<CharacterItem> = emptyList()

    fun searchForCharacters(characterName: String): Observable<List<CharacterItem>> {
        return searchCharactersRepository.getByKey(characterName)
                .map { it.items }
                .doOnNext { currentCharacterList = it }
    }

    fun getCharacterFromId(characterId: Int): Observable<CharacterItem> {
        return Observable.just(currentCharacterList.find { it.id == characterId })
    }
}