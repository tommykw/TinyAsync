package com.github.tommykw.tinyasync

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

internal class TinyAsync<T>(val ref: WeakReference<T>)

internal fun <T, R> TinyAsync<T>.ioThread(executor: Executor, block: (T) -> R) = 
    ref.get()?.let { executor.execute { block(it) } }

internal fun <T, R> TinyAsync<T>.mainThread(block: (T) -> R) =
    ioThread( Executor { Handler(Looper.getMainLooper()).post(it) }, block)

internal fun <T> T.dispatch(executorService: ExecutorService, block: TinyAsync<T>.() -> Unit): Future<Unit> =
    executorService.submit<Unit> { TinyAsync.(WeakReference(this).block) }
  
