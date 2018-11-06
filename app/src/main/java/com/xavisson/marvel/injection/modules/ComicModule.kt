package com.xavisson.marvel.injection.modules

import com.xavisson.marvel.data.comic.ComicApiClient
import com.xavisson.marvel.data.comic.SearchComicsApiDataSource
import com.xavisson.marvel.data.repository.CachePolicyTtl
import com.xavisson.marvel.data.repository.RxInMemoryCacheDataSource
import com.xavisson.marvel.domain.character.SearchCharactersItems
import com.xavisson.marvel.domain.comic.ComicApi
import com.xavisson.marvel.domain.comic.ComicResource
import com.xavisson.marvel.domain.comic.SearchComicItems
import com.xavisson.marvel.domain.repository.RxBaseRepository
import com.xavisson.marvel.domain.repository.TimeProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
open class ComicModule {

    companion object {
        private val REPO_VERSION: Int = 1
    }

    @Provides
    fun provideComicResource(
            comicRepository: RxBaseRepository<String, SearchComicItems>
    ): ComicResource {
        return ComicResource(comicRepository)
    }

    @Provides
    @Reusable
    fun providesSearchComicsRepository(
            searchComicsInMemoryDataSource: RxInMemoryCacheDataSource<String, SearchComicItems>,
            searchComicsApiDataSource: SearchComicsApiDataSource
    ): RxBaseRepository<String, SearchComicItems> =
            RxBaseRepository(
                    rxCacheableDataSources = listOf(searchComicsInMemoryDataSource),
                    rxWriteableDataSources = listOf(),
                    rxReadableDataSources = listOf(searchComicsApiDataSource)
            )

    @Provides
    @Reusable
    fun providesSearchComicApiDataSource(
            comicApi: ComicApi
    ): SearchComicsApiDataSource =
            SearchComicsApiDataSource(comicApi)

    @Provides
    @Reusable
    fun providesSearchComicsInMemoryDataSource(
            timeProvider: TimeProvider
    ): RxInMemoryCacheDataSource<String, SearchComicItems> =
            RxInMemoryCacheDataSource(
                    version = REPO_VERSION,
                    timeProvider = timeProvider,
                    policies = listOf(
                            CachePolicyTtl.oneMinute(timeProvider = timeProvider)
                    )
            )

    @Provides
    @Singleton
    fun provideComicApi(): ComicApi {
        return ComicApiClient()
    }
}