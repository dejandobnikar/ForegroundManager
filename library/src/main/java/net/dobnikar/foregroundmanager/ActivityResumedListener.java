package net.dobnikar.foregroundmanager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by dejandobnikar on 19/04/2017.
 */

abstract class ActivityResumedListener implements Application.ActivityLifecycleCallbacks {
    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // nothing
    }

    @Override public void onActivityStarted(Activity activity) {
        // nothing
    }

    @Override public void onActivityPaused(Activity activity) {
        // nothing
    }

    @Override public void onActivityStopped(Activity activity) {
        // nothing
    }

    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // nothing
    }

    @Override public void onActivityDestroyed(Activity activity) {
        // nothing
    }
}
