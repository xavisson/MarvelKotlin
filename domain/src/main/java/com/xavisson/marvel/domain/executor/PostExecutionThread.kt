package com.xavisson.marvel.domain.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}