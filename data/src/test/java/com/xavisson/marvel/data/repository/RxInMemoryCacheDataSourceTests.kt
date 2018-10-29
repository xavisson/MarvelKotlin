package com.xavisson.marvel.data.repository

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RxInMemoryCacheDataSourceTests {

    lateinit var rxInMemoryCacheDataSourceMother: RxInMemoryCacheDataSourceMother
    @Before
    fun setUp() {
        rxInMemoryCacheDataSourceMother = RxInMemoryCacheDataSourceMother()
    }

    @Test
    fun getByKeyWithNonExistentKeyShouldCompleteWithoutValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getByKeyWhenThereAreNoItemsShouldCompleteWithoutValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllWhenKeysIsNullShouldCompleteWithoutValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.getAll()
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllWhenKeysIsEmptyShouldCompleteWithEmptyList() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf())
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { values ->
            values.isEmpty()
        }
    }

    @Test
    fun getAllAfterRemoveLastItemOfItemsShouldCompletesWithoutValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1))
                .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getAll()
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { it.isEmpty() }
    }

    @Test
    fun getByKeyAfterAddOneItemShouldCompleteWithOneItem() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY1) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { value ->
            ANY_VALUE1 == value
        }
    }

    @Test
    fun getAllAfterAddOneItemShouldCompleteWithoutValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getByKeyWithExistentKeyAfterAddAllItemsShouldCompleteWithOneItem() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
                .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY2) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { value ->
            ANY_VALUE2 == value
        }
    }

    @Test
    fun getAllAfterAddAllItemsShouldCompleteWithAllItems() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { values ->
            values.contains(ANY_VALUE1)
            values.contains(ANY_VALUE2)
            values.contains(ANY_VALUE3)
        }
    }

    @Test
    fun getByKeyAfterRemoveThatItemShouldCompleteWithoudValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun getAllAfterRemoveAllExistentItemsShouldCompleteWithoutValue() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
                .flatMapCompletable { rxInMemoryCacheDataSource.deleteAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getAll()
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun getAllAfterAddAllItemsAndAddOneItemShouldCompleteWithAllValues() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver2 = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
                .flatMap { rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE5) }
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { values ->
            values.contains(ANY_VALUE1)
            values.contains(ANY_VALUE2)
            values.contains(ANY_VALUE3)
            values.contains(ANY_VALUE4)
            values.contains(ANY_VALUE5)
        }
    }

    @Test
    fun getByKeyWithNonExistentKeyAfterAddAllItemsShouldCompleteWithAllValues() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
                .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
                .test()
        testObserver.onComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getAll().test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { values ->
            values.contains(ANY_VALUE1)
            values.contains(ANY_VALUE2)
            values.contains(ANY_VALUE3)
            values.contains(ANY_VALUE4)
        }
    }

    // Invalid cache
    @Test
    fun getByKeyWithNonExistentKeyShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getByKeyWhenThereAreNoItemsShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllWhenKeysIsNullShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.getAll()
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllWhenKeysIsEmptyShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf())
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllAfterRemoveLastItemOfItemsShouldCompleteWithAnEmptyList() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1))
                .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getAll()
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { it.isEmpty() }
    }

    @Test
    fun getByKeyAfterAddOneItemShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY1) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllAfterAddOneItemShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getByKeyWithExistentKeyAfterAddAllItemsShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
                .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY2) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getAllAfterAddAllItemsShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun getByKeyAfterRemoveThatItemShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
                .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun getAllAfterRemoveAllExistentItemsShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
                .flatMapCompletable { rxInMemoryCacheDataSource.deleteAll() }
                .test()
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getAll()
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun getAllAfterAddAllItemsAndAddOneItemShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver2 = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
                .flatMap { rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE5) }
                .flatMap { rxInMemoryCacheDataSource.getAll() }
                .test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun getByKeyWithNonExistentKeyAfterAddAllItemsShouldCompleteWithoutValueBecauseCacheIsNotValid() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
                rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()
        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
                .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
                .test()
        testObserver.onComplete()
        testObserver.assertValueCount(0)
        val testObserver2 = rxInMemoryCacheDataSource.getAll().test()
        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    companion object {
        val ANY_KEY1 = AnyRepositoryKey(1)
        val ANY_KEY2 = AnyRepositoryKey(2)
        val ANY_KEY3 = AnyRepositoryKey(3)
        val ANY_KEY4 = AnyRepositoryKey(4)
        val ANY_KEY5 = AnyRepositoryKey(5)
        val NONEXISTENT_KEY = AnyRepositoryKey(99)
        val ANY_VALUE1 = AnyRepositoryValue(ANY_KEY1)
        val ANY_VALUE2 = AnyRepositoryValue(ANY_KEY2)
        val ANY_VALUE3 = AnyRepositoryValue(ANY_KEY3)
        val ANY_VALUE4 = AnyRepositoryValue(ANY_KEY4)
        val ANY_VALUE5 = AnyRepositoryValue(ANY_KEY5)
    }
}
