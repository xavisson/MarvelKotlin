package com.xavisson.marvel.lifecycle

import java.lang.RuntimeException
class PresenterException(override val cause: Throwable?, override val message: String?) : RuntimeException()
class PresenterNotAccessibleException constructor(override val message: String?) : RuntimeException(message) {
    constructor(cause: Throwable?, message: String?) : this(message) {
        initCause(cause)
    }
}