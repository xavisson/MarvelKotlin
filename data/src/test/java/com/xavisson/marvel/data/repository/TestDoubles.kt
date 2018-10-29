package com.xavisson.marvel.data.repository

import com.xavisson.marvel.domain.repository.Identifiable

data class AnyRepositoryKey(val key: Int)

data class AnyRepositoryValue(override val key: AnyRepositoryKey) : Identifiable<AnyRepositoryKey>