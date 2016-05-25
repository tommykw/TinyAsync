package com.github.tommykw.tinyasync

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

internal class TinyAsync<T>(val ref: WeakReference<T>)
