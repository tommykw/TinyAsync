package com.github.tommykw.tinyasync

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class TinyAsync<T>(val ref: WeakReference<T>) {
    init { require(ref == null, {"reference must not be null"}) }
}

fun <T, R> TinyAsync<T>.ioThread(executor: Executor, block: (T) -> R) =
    ref.get()?.let { executor.execute { block(it) } }

fun <T, R> TinyAsync<T>.mainThread(block: (T) -> R) =
    ioThread( Executor { Handler(Looper.getMainLooper()).post(it) }, block)

fun <T> T.dispatch(executorService: ExecutorService, block: TinyAsync<T>.() -> Unit): Future<Unit> =
    executorService.submit<Unit> { TinyAsync(WeakReference(this)).block() }
