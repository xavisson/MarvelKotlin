package com.xavisson.marvel.data.exception

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class MarvelServerException(
        message: String,
        private val response: Response<*>?,
        throwable: Throwable
) : IOException(message, throwable) {
    companion object {
        fun httpError(throwable: HttpException): MarvelServerException {
            val response = throwable.response()
            val message = response.code().toString() + " " + response.message()
            return MarvelServerException(message, response, throwable)
        }

        fun networkError(exception: Throwable): MarvelServerException {
            return MarvelServerException(exception.message!!, null, exception)
        }

        fun undefinedError(exception: Throwable): MarvelServerException {
            return MarvelServerException(exception.message!!, null, exception)
        }
    }

    fun getHTTPErrorStatus(): Int {
        return response?.code() ?: -1
    }

    fun isOutdatedAppVersionError(): Boolean {
        return getHTTPErrorStatus() == HttpURLConnection.HTTP_NOT_ACCEPTABLE
    }

    fun isUnauthorized(): Boolean {
        return getHTTPErrorStatus() == HttpURLConnection.HTTP_UNAUTHORIZED
    }

    fun isForbidden(): Boolean {
        return getHTTPErrorStatus() == HttpURLConnection.HTTP_FORBIDDEN
    }

    fun isNotFound(): Boolean {
        return getHTTPErrorStatus() == HttpURLConnection.HTTP_NOT_FOUND
    }

    fun isBadRequest(): Boolean {
        return getHTTPErrorStatus() == HttpURLConnection.HTTP_BAD_REQUEST
    }

    fun isConflict(): Boolean {
        return getHTTPErrorStatus() == HttpURLConnection.HTTP_CONFLICT
    }

    fun isServerUnreachable(): Boolean {
        val isHttpTimeoutError = getHTTPErrorStatus() == HttpURLConnection.HTTP_CLIENT_TIMEOUT
        return isServerError() || hasTimedOut() || isHttpTimeoutError
    }

    private fun isServerError(): Boolean {
        return getHTTPErrorStatus() in 500..509
    }

    fun hasTimedOut(): Boolean {
        try {
            return cause is TimeoutException || cause is SocketTimeoutException
        } catch (nullPointerException: NullPointerException) {
            return false
        }
    }

    fun isOffline(): Boolean {
        try {
            return cause is UnknownHostException
                    || cause is ConnectException
                    || cause is SocketException
        } catch (nullPointerException: NullPointerException) {
            return false
        }
    }

    fun isUndefined(): Boolean {
        return !hasTimedOut() && !isOffline()
    }

    fun getErrorBody(): ByteArray {
        return response?.errorBody()?.bytes() ?: ByteArray(0)
    }

    fun getErrorBodyString(): String? {
        return response?.errorBody()?.string()
    }
}