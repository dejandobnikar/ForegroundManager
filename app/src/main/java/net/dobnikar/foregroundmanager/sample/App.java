package net.dobnikar.foregroundmanager.sample;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import net.dobnikar.foregroundmanager.ForegroundListener;
import net.dobnikar.foregroundmanager.ForegroundManager;

public class App extends Application implements ForegroundListener {

    private NotificationManager notificationManager;

    @Override public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();

        final ForegroundManager foregroundManager = new ForegroundManager(this);
        foregroundManager.addListener(this);
    }

    @Override public void onForegroundStateChanged(boolean inForeground) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "general")
                .setContentTitle(inForeground ? "Foreground" : "Background")
                .setContentText(inForeground ? "App is now in foreground" : "App is now in background")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("general", "General", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
