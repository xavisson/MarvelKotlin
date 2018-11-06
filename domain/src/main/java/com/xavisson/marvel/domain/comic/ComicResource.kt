package com.xavisson.marvel.domain.comic

import com.xavisson.marvel.domain.repository.RxBaseRepository
import io.reactivex.Observable

class ComicResource(
        private val comicsRepository: RxBaseRepository<String, SearchComicItems>
) {

    fun getComicsFromCharacterId(characterId: Int): Observable<List<ComicItem>> {
        return comicsRepository.getByKey(characterId.toString())
                .map { it.items }
    }
}