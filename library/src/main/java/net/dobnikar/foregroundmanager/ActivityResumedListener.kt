package net.dobnikar.foregroundmanager

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

/**
 * Created by dejandobnikar on 19/04/2017.
 */
internal abstract class ActivityResumedListener : ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // nothing
    }

    override fun onActivityStarted(activity: Activity) {
        // nothing
    }

    override fun onActivityPaused(activity: Activity) {
        // nothing
    }

    override fun onActivityStopped(activity: Activity) {
        // nothing
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
        // nothing
    }

    override fun onActivityDestroyed(activity: Activity) {
        // nothing
    }
}
