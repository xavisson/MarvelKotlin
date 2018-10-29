package com.xavisson.marvel.data.repository

import com.xavisson.marvel.domain.repository.ApplicationTimeProvider
import com.xavisson.marvel.domain.repository.CacheItemPolicy
import com.xavisson.marvel.domain.repository.CachePolicy

class RxInMemoryCacheDataSourceMother {

    companion object {
        val CACHE_DATA_SOURCE_VERSION = 1
    }

    lateinit var cachePolicy: CachePolicy

    fun setupCachePolicyToReturnValidData() {
        cachePolicy = object : CachePolicy {
            override fun isValid(cacheItem: CacheItemPolicy): Boolean = true
        }
    }

    fun setupCachePolicyToReturnInvalidData() {
        cachePolicy = object : CachePolicy {
            override fun isValid(cacheItem: CacheItemPolicy): Boolean = false
        }
    }

    fun getRxInMemoryCacheDataSource(): RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            RxInMemoryCacheDataSource(CACHE_DATA_SOURCE_VERSION, ApplicationTimeProvider(), listOf(cachePolicy))
}