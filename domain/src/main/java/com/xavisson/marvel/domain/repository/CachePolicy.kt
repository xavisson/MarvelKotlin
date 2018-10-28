package com.xavisson.marvel.domain.repository

interface CachePolicy {
    fun isValid(cacheItem: CacheItemPolicy): Boolean
}

abstract class CacheItemPolicy(
        val version: Int,
        val timestamp: Long
)