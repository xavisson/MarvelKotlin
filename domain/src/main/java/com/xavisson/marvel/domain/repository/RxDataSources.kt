package com.xavisson.marvel.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import java.lang.Exception

interface DataSource

interface RxReadableDataSource<K, V> : DataSource {
    @Throws(Exception::class)
    fun getByKey(key: K): Observable<V>

    @Throws(Exception::class)
    fun getAll(): Observable<List<V>>
}

interface RxWriteableDataSource<K, V : Identifiable<K>> : DataSource {
    @Throws(Exception::class)
    fun addOrUpdate(value: V): Observable<V>

    @Throws(Exception::class)
    fun replaceAll(values: List<V>): Observable<List<V>>

    @Throws(Exception::class)
    fun deleteByKey(key: K): Completable

    @Throws(Exception::class)
    fun deleteAll(): Completable
}

interface RxCacheableDataSource<K, V : Identifiable<K>> : RxReadableDataSource<K, V>, RxWriteableDataSource<K, V> {
    val policies: List<CachePolicy>
    fun isValid(value: V): Boolean
}