package com.xavisson.marvel.domain.character

import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.domain.executor.applyScheduler
import io.reactivex.Observable


interface SearchForCharactersUseCase {
    fun execute(characterName: String): Observable<List<CharacterItem>>
}

class SearchForCharacters(
        private val characterResource: CharacterResource,
        private val threadScheduler: ThreadScheduler
) : SearchForCharactersUseCase {
    override fun execute(characterName: String): Observable<List<CharacterItem>> {
        return characterResource.searchForCharacters(characterName)
                .applyScheduler(threadScheduler)
    }
}