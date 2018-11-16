package net.dobnikar.foregroundmanager;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

/**
 * Created by dejandobnikar on 19/04/2017.
 */

abstract class TrimMemoryListener implements ComponentCallbacks2 {

    @Override public void onConfigurationChanged(Configuration newConfig) {
        // nothing
    }

    @Override public void onLowMemory() {
        // nothing
    }
}
