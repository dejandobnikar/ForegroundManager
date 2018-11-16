package net.dobnikar.foregroundmanager;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

public class ForegroundManager {

    private final List<ForegroundListener> listeners = new ArrayList<>();
    private boolean inForeground;

    public ForegroundManager(Application application) {

        application.registerComponentCallbacks(new TrimMemoryListener() {
            @Override public void onTrimMemory(int level) {
                if (level >= TrimMemoryListener.TRIM_MEMORY_UI_HIDDEN) {
                    setState(false);
                }
            }
        });

        application.registerActivityLifecycleCallbacks(new ActivityResumedListener() {
            @Override public void onActivityResumed(Activity activity) {
                setState(true);
            }
        });

        application.registerReceiver(new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                setState(false);
            }
        }, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    public void addListener(ForegroundListener listener) {
        synchronized (this) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }
    }

    public void removeListener(ForegroundListener listener) {
        synchronized (this) {
            final int i = listeners.indexOf(listener);
            if (i >= 0) {
                listeners.remove(i);
            }
        }
    }

    public boolean isInForeground() {
        return inForeground;
    }

    private void setState(boolean foreground) {
        synchronized (this) {
            if (inForeground != foreground) {
                inForeground = foreground;
                for (ForegroundListener listener : listeners) {
                    listener.onForegroundStateChanged(inForeground);
                }
            }
        }
    }
}
