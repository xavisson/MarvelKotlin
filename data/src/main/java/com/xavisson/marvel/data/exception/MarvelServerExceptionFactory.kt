package com.xavisson.marvel.data.exception

import io.reactivex.exceptions.CompositeException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class MarvelServerExceptionFactory {
    companion object {
        fun create(_throwable: Throwable): MarvelServerException {
            var throwable = _throwable
            when (throwable) {
                is CompositeException -> throwable = getFirstExceptionFromComposite(throwable)
                is HttpException -> return MarvelServerException.httpError(throwable)
                is IOException, is TimeoutException -> return MarvelServerException.networkError(throwable)
            }
            return MarvelServerException.undefinedError(throwable)
        }

        private fun getFirstExceptionFromComposite(throwable: Throwable): Throwable {
            return (throwable as CompositeException).exceptions[0]
        }
    }
}