package com.xavisson.marvel.injection.modules

import com.xavisson.marvel.data.character.CharacterApiClient
import com.xavisson.marvel.domain.character.CharacterApi
import dagger.Module
import dagger.Provides

@Module
class MarvelModule {

    @Provides
    fun provideCharacterApi(): CharacterApi {
        return CharacterApiClient()
    }
}