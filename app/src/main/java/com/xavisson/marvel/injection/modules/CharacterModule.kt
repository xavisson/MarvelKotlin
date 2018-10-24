package com.xavisson.marvel.injection.modules

import com.xavisson.marvel.data.character.CharacterApiClient
import com.xavisson.marvel.domain.character.CharacterApi
import com.xavisson.marvel.domain.character.CharacterResource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class CharacterModule {

    @Provides
    @Singleton
    fun provideCharacterResource(
            characterApi: CharacterApi
    ): CharacterResource {
        return CharacterResource(characterApi)
    }

    @Provides
    @Singleton
    fun provideCharacterApi(): CharacterApi {
        return CharacterApiClient()
    }
}