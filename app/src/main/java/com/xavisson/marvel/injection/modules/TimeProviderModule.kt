package com.xavisson.marvel.injection.modules

import com.xavisson.marvel.domain.repository.ApplicationTimeProvider
import com.xavisson.marvel.domain.repository.TimeProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
open class TimeProviderModule {

    @Provides
    @Reusable
    open fun providesTimeProvider(): TimeProvider = ApplicationTimeProvider()
}