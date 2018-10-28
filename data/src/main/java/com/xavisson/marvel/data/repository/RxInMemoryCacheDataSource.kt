package com.xavisson.marvel.data.repository

import com.xavisson.marvel.domain.repository.*
import io.reactivex.Completable
import io.reactivex.Observable

class RxInMemoryCacheDataSource<K, V : Identifiable<K>>(
        private val version: Int,
        private val timeProvider: TimeProvider,
        override val policies: List<CachePolicy>
) : RxCacheableDataSource<K, V> {

    private var items: MutableMap<K, CacheItem<V>> = mutableMapOf()
    private var keys: MutableList<K>? = null
    private lateinit var keysCache: CacheKeys

    override fun getByKey(key: K): Observable<V> =
            Observable.create { observer ->
                if (items.isNotEmpty()) {
                    val value: V? = items[key]?.value
                    if (value != null) {
                        if (isValid(value))
                            observer.onNext(value)
                        else {
                            removeByKey(key)
                            keys = null
                        }
                    }
                }
                observer.onComplete()
            }

    override fun getAll(): Observable<List<V>> =
            Observable.create { observer ->
                if (keys != null) {
                    val values: List<V> = keys!!.mapNotNull { key -> items[key]?.value }
                    if ((values.isEmpty() && isKeysListValid())
                            || (values.isNotEmpty() && values.all { isValid(it) }))
                        observer.onNext(values)
                    else
                        removeAll()
                }
                observer.onComplete()
            }

    override fun addOrUpdate(value: V): Observable<V> =
            Observable.create { observer ->
                createOrUpdate(value)
                observer.onNext(value)
                observer.onComplete()
            }

    override fun replaceAll(values: List<V>): Observable<List<V>> =
            Observable.create { observer ->
                keysCache = CacheKeys(version = version,
                        timestamp = timeProvider.currentTimeMillis())
                if (keys == null) {
                    keys = mutableListOf()
                }
                values.forEach { value ->
                    createOrUpdate(value)
                }
                observer.onNext(values)
                observer.onComplete()
            }

    override fun deleteByKey(key: K): Completable =
            Completable.create { observer ->
                removeByKey(key)
                observer.onComplete()
            }

    override fun deleteAll(): Completable =
            Completable.create { observer ->
                removeAll()
                observer.onComplete()
            }

    override fun isValid(value: V): Boolean =
            items[value.key]?.let { cacheItem ->
                policies.all { it.isValid(cacheItem) }
            } ?: false

    private fun isKeysListValid(): Boolean =
            policies.all { it.isValid(keysCache) }

    private fun createOrUpdate(value: V) {
        items.put(value.key, cacheItemFor(value))
        if (keys != null) {
            val key = keys!!.find { key -> key == value.key }
            if (key == null) {
                keys!!.add(value.key)
            }
        }
    }

    private fun removeByKey(key: K) {
        items.remove(key)
        keys?.remove(key)
    }

    private fun removeAll() {
        items.clear()
        keys = null
    }

    private fun cacheItemFor(value: V): CacheItem<V> =
            CacheItem(value = value,
                    version = version,
                    timestamp = timeProvider.currentTimeMillis())

    private class CacheKeys(version: Int,
                            timestamp: Long) : CacheItemPolicy(version, timestamp)
}