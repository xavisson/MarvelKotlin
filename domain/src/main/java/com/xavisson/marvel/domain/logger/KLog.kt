package com.xavisson.marvel.domain.logger

import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface HasTag {
	val TAG: String
}

enum class LogLevel {
	ERROR, WARN, INFO, DEBUG, VERBOSE
}

interface LoggerTree {
	fun log(tag: () -> String, level: LogLevel, msg: () -> String, throwable: Throwable? = null)
}

class KotlinLogger(val tag: () -> String) {
	// ERROR
	fun e(msg: () -> String) = Kog.log(tag, LogLevel.ERROR, msg)

	fun e(tag: () -> String, msg: () -> String) = Kog.log(tag, LogLevel.ERROR, msg)
	fun e(tag: () -> String, throwable: Throwable, msg: () -> String) =
			Kog.log(tag, LogLevel.ERROR, msg, throwable)

	fun e(throwable: Throwable, msg: () -> String) = e(tag, throwable, msg)
	// WARNING
	fun w(msg: () -> String) = Kog.log(tag, LogLevel.WARN, msg)

	fun w(tag: () -> String, msg: () -> String) = Kog.log(tag, LogLevel.WARN, msg)
	// INFO
	fun i(msg: () -> String) = Kog.log(tag, LogLevel.INFO, msg)

	fun i(tag: () -> String, msg: () -> String) = Kog.log(tag, LogLevel.INFO, msg)
	// DEBUG
	fun d(msg: () -> String) = Kog.log(tag, LogLevel.DEBUG, msg)

	fun d(tag: () -> String, msg: () -> String) = Kog.log(tag, LogLevel.DEBUG, msg)
	// VERBOSE
	fun v(msg: () -> String) = Kog.log(tag, LogLevel.VERBOSE, msg)

	fun v(tag: () -> String, msg: () -> String) = Kog.log(tag, LogLevel.VERBOSE, msg)
	private fun getStackTraceString(throwable: Throwable): String {
		val stringWriter = StringWriter(256)
		val printWriter = PrintWriter(stringWriter, false)
		throwable.printStackTrace(printWriter)
		printWriter.flush()
		return stringWriter.toString()
	}
}

object Kog : KotlinLoggerForest()

val Any.Logger: KotlinLogger by Kog.getLogDelegate()
val Any.TAG: String
	get() = if (this is HasTag) {
		this.TAG
	} else {
		"${this.javaClass.simpleName} [MARVEL]"
	}

open class KotlinLoggerForest() : LoggerTree {
	val forest: MutableList<LoggerTree> = CopyOnWriteArrayList<LoggerTree>()
	override fun log(tag: () -> String, level: LogLevel, msg: () -> String, throwable: Throwable?) {
		forest.forEach { it.log(tag, level, msg, throwable) }
	}

	fun plant(loggerTree: LoggerTree) {
		if (loggerTree == this) {
			throw IllegalArgumentException("You can't plant the KotlinForests.")
		}
		forest.add(loggerTree)
	}

	fun uprootAll() {
		forest.clear()
	}

	fun uproot(loggerTree: LoggerTree) {
		forest.remove(loggerTree)
	}

	fun <T : Any> getLogDelegate(): ReadOnlyProperty<T, KotlinLogger> {
		return object : ReadOnlyProperty<T, KotlinLogger> {
			override fun getValue(thisRef: T, property: KProperty<*>): KotlinLogger {
				val tag = thisRef.TAG
				return KotlinLogger({ tag })
			}
		}
	}
}
