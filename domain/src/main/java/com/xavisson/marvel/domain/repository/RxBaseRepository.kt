package com.xavisson.marvel.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable

open class RxBaseRepository<K, V : Identifiable<K>>(
        private val rxCacheableDataSources: List<RxCacheableDataSource<K, V>> = listOf(),
        private val rxWriteableDataSources: List<RxWriteableDataSource<K, V>> = listOf(),
        private val rxReadableDataSources: List<RxReadableDataSource<K, V>> = listOf()
) : RxReadableDataSource<K, V>, RxWriteableDataSource<K, V> {

    override fun getByKey(key: K): Observable<V> = getByKey(key, ReadPolicy.READ_ALL)
    fun getByKey(key: K, readPolicy: ReadPolicy): Observable<V> =
            Observable.concat(
                    getByKeyFromDataSources(key, rxCacheableDataSources).applyReadPolicyCondition(readPolicy.useCache()),
                    getByKeyFromDataSources(key, rxReadableDataSources).applyReadPolicyCondition(readPolicy.useReadable())
                            .flatMap { readableValue ->
                                addOrUpdateInDataSources(readableValue, rxCacheableDataSources)
                                        .switchIfEmpty(Observable.just(readableValue))
                            }
            ).firstElement()
                    .toObservable()

    override fun getAll(): Observable<List<V>> = getAll(ReadPolicy.READ_ALL)
    fun getAll(readPolicy: ReadPolicy): Observable<List<V>> =
            Observable.concat(
                    getAllFromDataSources(rxCacheableDataSources).applyReadPolicyConditionOnList(readPolicy.useCache()),
                    getAllFromDataSources(rxReadableDataSources).applyReadPolicyConditionOnList(readPolicy.useReadable())
                            .flatMap { readableValue ->
                                replaceAllInDataSources(readableValue, rxCacheableDataSources)
                                        .switchIfEmpty(Observable.just(readableValue))
                            }
            ).firstElement()
                    .toObservable()

    override fun addOrUpdate(value: V): Observable<V> =
            addOrUpdateInDataSources(value, rxWriteableDataSources)
                    .concatWith(addOrUpdateInDataSources(value, rxCacheableDataSources))

    override fun replaceAll(values: List<V>): Observable<List<V>> =
            replaceAllInDataSources(values, rxWriteableDataSources)
                    .concatWith(replaceAllInDataSources(values, rxCacheableDataSources))

    override fun deleteByKey(key: K): Completable =
            deleteByKeyFromDataSources(key, rxWriteableDataSources)
                    .concatWith(deleteByKeyFromDataSources(key, rxCacheableDataSources))

    override fun deleteAll(): Completable =
            deleteAllFromDataSources(rxWriteableDataSources)
                    .concatWith(deleteAllFromDataSources(rxCacheableDataSources))

    private fun getByKeyFromDataSources(key: K, dataSources: List<RxReadableDataSource<K, V>>): Observable<V> =
            Observable.fromIterable(dataSources.reversed())
                    .concatMap { it.getByKey(key) }
                    .firstElement()
                    .toObservable()

    private fun getAllFromDataSources(dataSources: List<RxReadableDataSource<K, V>>): Observable<List<V>> =
            Observable.fromIterable(dataSources.reversed())
                    .concatMap { it.getAll() }
                    .firstElement()
                    .toObservable()

    private fun addOrUpdateInDataSources(value: V, dataSources: List<RxWriteableDataSource<K, V>>): Observable<V> =
            Observable.fromIterable(dataSources.reversed())
                    .concatMap { it.addOrUpdate(value) }

    private fun replaceAllInDataSources(values: List<V>, dataSources: List<RxWriteableDataSource<K, V>>): Observable<List<V>> =
            Observable.fromIterable(dataSources.reversed())
                    .concatMap { it.replaceAll(values) }

    private fun deleteByKeyFromDataSources(key: K, dataSources: List<RxWriteableDataSource<K, V>>): Completable =
            Observable.fromIterable(dataSources.reversed())
                    .flatMapCompletable { it.deleteByKey(key) }

    private fun deleteAllFromDataSources(dataSources: List<RxWriteableDataSource<K, V>>): Completable =
            Observable.fromIterable(dataSources.reversed())
                    .flatMapCompletable { it.deleteAll() }

    private fun Observable<V>.applyReadPolicyCondition(condition: Boolean): Observable<V> {
        return if (condition)
            this
        else
            Observable.empty()
    }

    private fun Observable<List<V>>.applyReadPolicyConditionOnList(condition: Boolean): Observable<List<V>> {
        return if (condition)
            this
        else
            Observable.empty()
    }
}

/**
 * Represents an object that can be identified uniquely by an object of the parametrized class.
 * @param <K> The class of the key used to identify objects of this class.
</K> */
interface Identifiable<K> {
    val key: K
}