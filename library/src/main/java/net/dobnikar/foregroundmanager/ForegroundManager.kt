package net.dobnikar.foregroundmanager

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class ForegroundManager(private val application: Application) {

    private val listeners = mutableSetOf<ForegroundListener>()
    private val lambdaListeners = mutableSetOf<(Boolean) -> Unit>()

    @get:JvmName("isInForeground")
    var inForeground = false
        private set

    private val memoryListener = object : TrimMemoryListener() {
        override fun onTrimMemory(level: Int) {
            if (level >= TRIM_MEMORY_UI_HIDDEN) {
                setState(false)
            }
        }
    }

    private val lifecycleListener = object : ActivityResumedListener() {
        override fun onActivityResumed(activity: Activity) {
            setState(true)
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            setState(false)
        }
    }

    init {
        application.registerComponentCallbacks(memoryListener)
        application.registerActivityLifecycleCallbacks(lifecycleListener)
        application.registerReceiver(broadcastReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }

    fun destroy() {
        application.unregisterComponentCallbacks(memoryListener)
        application.unregisterActivityLifecycleCallbacks(lifecycleListener)
        application.unregisterReceiver(broadcastReceiver)
        listeners.clear()
    }

    fun addListener(listener: (Boolean) -> Unit) {
        synchronized(this) {
            lambdaListeners.add(listener)
        }
    }

    fun addListener(listener: ForegroundListener) {
        synchronized(this) {
            listeners.add(listener)
        }
    }

    fun removeListener(listener: ForegroundListener) {
        synchronized(this) {
            listeners.remove(listener)
        }
    }

    fun removeListener(listener: (Boolean) -> Unit) {
        synchronized(this) {
            lambdaListeners.remove(listener)
        }
    }

    private fun setState(foreground: Boolean) {
        synchronized(this) {
            if (inForeground != foreground) {
                inForeground = foreground
                listeners.forEach { it.onForegroundStateChanged(inForeground) }
                lambdaListeners.forEach { it(inForeground) }
            }
        }
    }
}
