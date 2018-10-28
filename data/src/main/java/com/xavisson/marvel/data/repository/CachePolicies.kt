package com.xavisson.marvel.data.repository

import com.xavisson.marvel.domain.repository.CacheItemPolicy
import com.xavisson.marvel.domain.repository.CachePolicy
import com.xavisson.marvel.domain.repository.TimeProvider
import java.util.concurrent.TimeUnit

class CachePolicyTtl(
        ttl: Int,
        timeUnit: TimeUnit,
        private val timeProvider: TimeProvider
) : CachePolicy {

    private val ttlMillis: Long = timeUnit.toMillis(ttl.toLong())

    override fun isValid(cacheItem: CacheItemPolicy): Boolean {
        val lifeTime = cacheItem.timestamp + ttlMillis
        return lifeTime > timeProvider.currentTimeMillis()
    }

    companion object {
        fun oneMinute(timeProvider: TimeProvider): CachePolicyTtl {
            return CachePolicyTtl(ttl = 1, timeUnit = TimeUnit.MINUTES, timeProvider = timeProvider)
        }
    }
}