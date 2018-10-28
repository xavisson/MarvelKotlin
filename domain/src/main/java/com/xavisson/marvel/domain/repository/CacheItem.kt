package com.xavisson.marvel.domain.repository

class CacheItem<out T>(
        val value: T,
        version: Int,
        timestamp: Long
) : CacheItemPolicy(version, timestamp)