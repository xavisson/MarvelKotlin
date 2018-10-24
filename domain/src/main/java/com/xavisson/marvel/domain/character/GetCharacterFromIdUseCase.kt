package com.xavisson.marvel.domain.character

import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.domain.executor.applyScheduler
import io.reactivex.Observable

interface GetCharacterFromIdUseCase {
    fun execute(characterId: Int): Observable<CharacterItem>
}

class GetCharacterFromId(
        private val characterResource: CharacterResource,
        private val threadScheduler: ThreadScheduler
) : GetCharacterFromIdUseCase {
    override fun execute(characterId: Int): Observable<CharacterItem> {
        return characterResource.getCharacterFromId(characterId)
                .applyScheduler(threadScheduler)
    }
}