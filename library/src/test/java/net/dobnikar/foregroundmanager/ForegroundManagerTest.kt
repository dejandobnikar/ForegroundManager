package net.dobnikar.foregroundmanager

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.ComponentCallbacks2
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ForegroundManagerTest {

    private lateinit var foregroundManager: ForegroundManager
    private lateinit var app: TestApplication

    @Before
    fun setUp() {
        app = TestApplication()
        foregroundManager = ForegroundManager(app)
    }

    @Test
    fun isInForeground() {

        var called = false
        var foreground = false

        foregroundManager.addListener(object : ForegroundListener {
            override fun onForegroundStateChanged(inForeground: Boolean) {
                called = true
                foreground = inForeground
            }
        })

        app.triggerForeground()

        assertTrue(called)
        assertTrue(foreground)
    }

    @Test
    fun isInBackground() {

        app.triggerForeground()

        var called = false
        var foreground = true

        foregroundManager.addListener(object : ForegroundListener {
            override fun onForegroundStateChanged(inForeground: Boolean) {
                called = true
                foreground = inForeground
            }
        })

        app.triggerBackground()

        assertTrue(called)
        assertFalse(foreground)
    }

    @Test
    fun isInForegroundFun() {

        var called = false
        var foreground = false

        foregroundManager.addListener {
            called = true
            foreground = it
        }

        app.triggerForeground()

        assertTrue(called)
        assertTrue(foreground)
    }

    @Test
    fun isInForegroundFunRemoved() {

        var called = false
        var foreground = false

        val listener = { b: Boolean ->
            called = true
            foreground = true
        }

        foregroundManager.addListener(listener)
        foregroundManager.removeListener(listener)

        app.triggerForeground()

        assertFalse(called)
        assertFalse(foreground)
    }

    @Test
    fun isInForegroundRemoved() {

        var called = false
        var foreground = false

        val listener = object : ForegroundListener {
            override fun onForegroundStateChanged(inForeground: Boolean) {
                called = true
                foreground = true
            }
        }
        foregroundManager.addListener(listener)
        foregroundManager.removeListener(listener)

        app.triggerForeground()

        assertFalse(called)
        assertFalse(foreground)
    }

    @Test
    fun isInForegroundBoth() {

        var called = 0
        var foreground = 0

        foregroundManager.addListener(object : ForegroundListener {
            override fun onForegroundStateChanged(inForeground: Boolean) {
                called++
                foreground++
            }
        })

        foregroundManager.addListener {
            called++
            foreground++
        }

        app.triggerForeground()

        assertEquals(2, called)
        assertEquals(2, foreground)
    }

    private class TestApplication : Application() {

        private lateinit var callback: ComponentCallbacks2
        private lateinit var activityCallback: ActivityLifecycleCallbacks

        override fun registerComponentCallbacks(callback: ComponentCallbacks) {
            super.registerComponentCallbacks(callback)
            if (callback is ComponentCallbacks2) {
                this.callback = callback
            }
        }

        override fun registerActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks) {
            super.registerActivityLifecycleCallbacks(callback)
            activityCallback = callback
        }

        fun triggerBackground() {
            callback.onTrimMemory(ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        }

        fun triggerForeground() {
            activityCallback.onActivityResumed(Activity())
        }
    }

}
