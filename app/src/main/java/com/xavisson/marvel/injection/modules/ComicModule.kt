package com.xavisson.marvel.injection.modules

import com.xavisson.marvel.data.comic.ComicApiClient
import com.xavisson.marvel.domain.comic.ComicApi
import com.xavisson.marvel.domain.comic.ComicResource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ComicModule {

    @Provides
    fun provideComicResource(
            characterApi: ComicApi
    ): ComicResource {
        return ComicResource(characterApi)
    }

    @Provides
    @Singleton
    fun provideComicApi(): ComicApi {
        return ComicApiClient()
    }
}