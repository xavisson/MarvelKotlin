package com.xavisson.marvel.domain.comic

import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.domain.executor.applyScheduler
import io.reactivex.Observable

interface GetComicsFromCharacterIdUseCase {
    fun execute(characterId: Int): Observable<List<ComicItem>>
}

class GetComicsFromCharacterId(
        private val comicResource: ComicResource,
        private val threadScheduler: ThreadScheduler
) : GetComicsFromCharacterIdUseCase {
    override fun execute(characterId: Int): Observable<List<ComicItem>> {
        return comicResource.getComicsFromCharacterId(characterId)
                .applyScheduler(threadScheduler)
    }
}
