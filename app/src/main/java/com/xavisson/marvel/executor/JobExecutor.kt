package com.xavisson.marvel.executor

import com.xavisson.marvel.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class JobExecutor : ThreadExecutor {

    companion object {
        const private val INITIAL_POOL_SIZE = 3
        const private val MAX_POOL_SIZE = 5
        // Sets the amount of time an idle thread waits before terminating
        const private val KEEP_ALIVE_TIME = 10
        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

    private val threadPoolExecutor: ThreadPoolExecutor

    init {
        val workQueue = LinkedBlockingQueue<Runnable>()
        val threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(
                INITIAL_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME.toLong(),
                KEEP_ALIVE_TIME_UNIT,
                workQueue,
                threadFactory)
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }
        this.threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        companion object {
            private val THREAD_NAME = "android_"
            private val counter = 0
        }

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter)
        }
    }
}