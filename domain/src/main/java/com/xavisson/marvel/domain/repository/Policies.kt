package com.xavisson.marvel.domain.repository

/**
 * Value to specify modifiers over the retrieval operations on repositories and data sources.
 */
enum class ReadPolicy {
    CACHE_ONLY,
    READABLE_ONLY,
    READ_ALL;

    fun useCache(): Boolean {
        return this == CACHE_ONLY || this == READ_ALL
    }

    fun useReadable(): Boolean {
        return this == READABLE_ONLY || this == READ_ALL
    }
}