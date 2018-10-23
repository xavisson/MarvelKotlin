package com.xavisson.marvel.data.client

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class MarvelApiClient(networkInterceptors: Set<Interceptor>) {

    private val DEFAULT_TIMEOUT_SECONDS: Long = 20
    private val WEBSOCKET_PING_INTERVAL_SECONDS: Long = 10
    private val DEFAULT_READ_TIMEOUT_SECONDS: Long = 45
    private val NO_TIMEOUT_SECONDS: Long = 0

    private val logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val okClient = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NO_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pingInterval(WEBSOCKET_PING_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addNetworkInterceptors(networkInterceptors)
            .build()

    fun getClient(): OkHttpClient {
        return okClient
    }

    private fun OkHttpClient.Builder.addNetworkInterceptors(networkInterceptors: Set<Interceptor>): OkHttpClient.Builder {
        interceptors().addAll(networkInterceptors)
        return this
    }
}
