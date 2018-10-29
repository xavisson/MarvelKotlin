package com.xavisson.marvel.domain.repository

import com.nhaarman.mockito_kotlin.doReturn
import io.reactivex.Observable
import org.amshove.kluent.any
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import java.util.*

class RxBaseRepositoryMother(
        private val rxCacheableDataSource: RxCacheableDataSource<AnyRepositoryKey, AnyRepositoryValue>,
        private val rxWriteableDataSource: RxWriteableDataSource<AnyRepositoryKey, AnyRepositoryValue>,
        private val rxReadableDataSource: RxReadableDataSource<AnyRepositoryKey, AnyRepositoryValue>
) {
    fun givenAnRxBaseRepositoryWithCacheableAndReadableDataSources(): RxBaseRepository<AnyRepositoryKey, AnyRepositoryValue> =
            RxBaseRepository(
                    rxCacheableDataSources = listOf(rxCacheableDataSource),
                    rxWriteableDataSources = listOf(),
                    rxReadableDataSources = listOf(rxReadableDataSource)
            )

    fun setupCacheableDataSourceToReturnNothingByKey() {
        `when`(rxCacheableDataSource.getByKey(any(AnyRepositoryKey::class)))
                .doReturn(Observable.create { observer ->
                    observer.onComplete()
                })
    }

    fun setupCacheableDataSourceToReturnValueByKey(anyRepositoryValue: AnyRepositoryValue) {
        `when`(rxCacheableDataSource.getByKey(any(AnyRepositoryKey::class)))
                .doReturn(Observable.create { observer ->
                    observer.onNext(anyRepositoryValue)
                    observer.onComplete()
                })
    }

    fun setupCacheableDataSourceToAddOrUpdateAValue() {
        `when`(rxCacheableDataSource.addOrUpdate(any(AnyRepositoryValue::class)))
                .thenAnswer { invocation ->
                    Observable.create<AnyRepositoryValue> { observer ->
                        observer.onNext(invocation.arguments[0] as AnyRepositoryValue)
                        observer.onComplete()
                    }
                }
    }

    fun setupReadableDataSourceToReturnValueByKey(anyRepositoryValue: AnyRepositoryValue) {
        `when`(rxReadableDataSource.getByKey(any(AnyRepositoryKey::class)))
                .doReturn(Observable.create { observer ->
                    observer.onNext(anyRepositoryValue)
                    observer.onComplete()
                })
    }

    fun setupCacheableDataSourceToReturnNothingAll() {
        `when`(rxCacheableDataSource.getAll())
                .doReturn(Observable.create { observer ->
                    observer.onComplete()
                })
    }

    fun setupCacheableDataSourceToReturnAllValues(allValues: List<AnyRepositoryValue>) {
        `when`(rxCacheableDataSource.getAll())
                .doReturn(Observable.create { observer ->
                    observer.onNext(allValues)
                    observer.onComplete()
                })
    }

    fun setupCacheableDataSourceToAddOrUpdateAllValues() {
        `when`(rxCacheableDataSource.replaceAll(ArgumentMatchers.anyList()))
                .thenAnswer { invocation ->
                    Observable.create<List<AnyRepositoryValue>> { observer ->
                        observer.onNext(invocation.arguments[0] as List<AnyRepositoryValue>)
                        observer.onComplete()
                    }
                }
    }

    fun setupReadableDataSourceToReturnAllValues(allValues: List<AnyRepositoryValue>) {
        `when`(rxReadableDataSource.getAll())
                .doReturn(Observable.create { observer ->
                    observer.onNext(allValues)
                    observer.onComplete()
                })
    }

    companion object {
        val ANY_KEY = AnyRepositoryKey(42)
        val ANY_VALUE = AnyRepositoryValue(ANY_KEY)
        val ALL_VALUES: List<AnyRepositoryValue>
            get() {
                val values = LinkedList<AnyRepositoryValue>()
                for (i in 0..9) {
                    values.add(AnyRepositoryValue(AnyRepositoryKey(i)))
                }
                return values
            }
    }
}