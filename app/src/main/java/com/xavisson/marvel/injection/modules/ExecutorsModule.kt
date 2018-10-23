package com.xavisson.marvel.injection.modules

import com.xavisson.marvel.domain.executor.PostExecutionThread
import com.xavisson.marvel.domain.executor.ThreadExecutor
import com.xavisson.marvel.domain.executor.ThreadScheduler
import com.xavisson.marvel.executor.DefaultThreadScheduler
import com.xavisson.marvel.executor.JobExecutor
import com.xavisson.marvel.executor.UIPostExecutionThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ExecutorsModule {

    @Provides
    @Singleton
    open fun providesThreadExecutor(): ThreadExecutor = JobExecutor()

    @Provides
    @Singleton
    open fun providesPostExecutionThread(): PostExecutionThread = UIPostExecutionThread()

    @Provides
    @Singleton
    open fun providesThreadScheduler(
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): ThreadScheduler {
        return DefaultThreadScheduler(
                threadExecutor = threadExecutor,
                postExecutionThread = postExecutionThread
        )
    }
}