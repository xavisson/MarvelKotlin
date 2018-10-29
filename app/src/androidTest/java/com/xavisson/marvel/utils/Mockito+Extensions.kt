package com.xavisson.marvel.utils

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

fun <T> whenn(methodCall: T): OngoingStubbing<T> {
    return Mockito.`when`(methodCall)
}

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T