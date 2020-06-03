package net.dobnikar.foregroundmanager

import android.content.ComponentCallbacks2
import android.content.res.Configuration

/**
 * Created by dejandobnikar on 19/04/2017.
 */
internal abstract class TrimMemoryListener : ComponentCallbacks2 {
    override fun onConfigurationChanged(newConfig: Configuration) {
        // nothing
    }

    override fun onLowMemory() {
        // nothing
    }
}
