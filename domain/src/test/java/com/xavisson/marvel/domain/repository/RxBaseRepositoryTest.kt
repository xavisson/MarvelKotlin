package com.xavisson.marvel.domain.repository

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RxBaseRepositoryTest {

    @Mock
    lateinit var readableDataSource: RxReadableDataSource<AnyRepositoryKey, AnyRepositoryValue>

    @Mock
    lateinit var writeableDataSource: RxWriteableDataSource<AnyRepositoryKey, AnyRepositoryValue>

    @Mock
    lateinit var cacheableDataSource: RxCacheableDataSource<AnyRepositoryKey, AnyRepositoryValue>

    private lateinit var rxRepositoryMother: RxBaseRepositoryMother

    @Before
    fun setUp() {
        rxRepositoryMother = RxBaseRepositoryMother(
                rxReadableDataSource = readableDataSource,
                rxWriteableDataSource = writeableDataSource,
                rxCacheableDataSource = cacheableDataSource
        )
    }

    @Test
    fun shouldEmitAValueFromReadableDataSourceAndAddItToCacheableDataSource() {
        val anyRepositoryValue: AnyRepositoryValue = RxBaseRepositoryMother.ANY_VALUE
        rxRepositoryMother.setupCacheableDataSourceToReturnNothingByKey()
        rxRepositoryMother.setupCacheableDataSourceToAddOrUpdateAValue()
        rxRepositoryMother.setupReadableDataSourceToReturnValueByKey(anyRepositoryValue)
        val rxBaseRepository = rxRepositoryMother.givenAnRxBaseRepositoryWithCacheableAndReadableDataSources()
        val testObserver = rxBaseRepository.getByKey(RxBaseRepositoryMother.ANY_KEY).test()
        testObserver.assertComplete()
        testObserver.assertValue { value ->
            anyRepositoryValue == value
        }
    }

    @Test
    fun shouldEmitAValueFromCacheableDataSource() {
        val anyRepositoryValue: AnyRepositoryValue = RxBaseRepositoryMother.ANY_VALUE
        rxRepositoryMother.setupCacheableDataSourceToReturnValueByKey(anyRepositoryValue)
        val rxBaseRepository = rxRepositoryMother.givenAnRxBaseRepositoryWithCacheableAndReadableDataSources()
        val testObserver = rxBaseRepository.getByKey(RxBaseRepositoryMother.ANY_KEY).test()
        testObserver.assertComplete()
        testObserver.assertValue { value ->
            anyRepositoryValue == value
        }
    }

    @Test
    fun shouldEmitAllValuesFromReadableDataSourceAndAddItToCacheableDataSource() {
        val anyRepositoryValues: List<AnyRepositoryValue> = RxBaseRepositoryMother.ALL_VALUES
        rxRepositoryMother.setupCacheableDataSourceToReturnNothingAll()
        rxRepositoryMother.setupCacheableDataSourceToAddOrUpdateAllValues()
        rxRepositoryMother.setupReadableDataSourceToReturnAllValues(anyRepositoryValues)
        val rxBaseRepository = rxRepositoryMother.givenAnRxBaseRepositoryWithCacheableAndReadableDataSources()
        val testObserver = rxBaseRepository.getAll().test()
        testObserver.assertComplete()
        testObserver.assertValue { values ->
            anyRepositoryValues == values
        }
    }

    @Test
    fun shouldEmitAllValuesFromCacheableDataSource() {
        val anyRepositoryValues: List<AnyRepositoryValue> = RxBaseRepositoryMother.ALL_VALUES
        rxRepositoryMother.setupCacheableDataSourceToReturnAllValues(anyRepositoryValues)
        val rxBaseRepository = rxRepositoryMother.givenAnRxBaseRepositoryWithCacheableAndReadableDataSources()
        val testObserver = rxBaseRepository.getAll().test()
        testObserver.assertComplete()
        testObserver.assertValue { values ->
            anyRepositoryValues == values
        }
    }

    @Test
    fun shouldEmitUpdatedModelAfterUpdateIt() {
        val anyRepositoryValue: AnyRepositoryValue = RxBaseRepositoryMother.ANY_VALUE
        val newRepositoryKey = AnyRepositoryKey(111111)
        rxRepositoryMother.setupReadableDataSourceToReturnValueByKey(anyRepositoryValue)
        rxRepositoryMother.setupCacheableDataSourceToReturnValueByKey(anyRepositoryValue)
        rxRepositoryMother.setupCacheableDataSourceToAddOrUpdateAValue()
        val rxBaseRepository = rxRepositoryMother.givenAnRxBaseRepositoryWithCacheableAndReadableDataSources()
        val testObserver = rxBaseRepository.getByKey(anyRepositoryValue.key).flatMap {
            rxBaseRepository.addOrUpdate(it.copy(key = AnyRepositoryKey(111111)))
        }.test()
        testObserver.assertComplete()
        testObserver.assertValue { value ->
            newRepositoryKey == value.key
        }
    }
}