package net.dobnikar.foregroundmanager.sample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import net.dobnikar.foregroundmanager.ForegroundListener
import net.dobnikar.foregroundmanager.ForegroundManager

class App : Application(), ForegroundListener {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        val foregroundManager = ForegroundManager(this)
        foregroundManager.addListener(this)
    }

    override fun onForegroundStateChanged(inForeground: Boolean) {
        val builder = NotificationCompat.Builder(this, "general")
                .setContentTitle(if (inForeground) "Foreground" else "Background")
                .setContentText(if (inForeground) "App is now in foreground" else "App is now in background")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("general", "General", importance)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
