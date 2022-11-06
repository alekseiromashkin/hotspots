package com.hotspots.android.apps.clusterization.feature.readservice.presentation

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.hotspots.android.apps.clusterization.feature.map.R
import com.hotspots.android.apps.clusterization.feature.readservice.di.DaggerReadHotspotsServiceComponent
import com.hotspots.android.apps.clusterization.feature.readservice.di.ReadHotspotsServiceDependenciesProvider
import com.hotspots.android.apps.clusterization.feature.readservice.model.HotspotsReaderRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

private const val CHANNEL_ID = "channel.read_hotspots"
private const val NOTIFICATION_ID = 1

class ReadHotspotsService : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    @Inject
    internal lateinit var repository: HotspotsReaderRepository

    private lateinit var notificationManager: NotificationManager

    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        val component = DaggerReadHotspotsServiceComponent
            .builder()
            .dependencies(ReadHotspotsServiceDependenciesProvider.dependencies)
            .build()
        component.inject(this)

        val className = "com.hotspots.android.apps.clusterization.launcher.presentation.LauncherActivity"
        val clazz = Class.forName(className)
        val notificationIntent = Intent(this, clazz)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.read_hotspots_service_title))
            .setSmallIcon(R.drawable.ic_sync)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Read Hotspots Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(serviceChannel)
        }

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        readHotspots()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationManager.cancel(NOTIFICATION_ID)
        serviceJob.cancel()
    }

    private fun readHotspots() {
        serviceScope.launch {
            withContext(Dispatchers.IO) {
                repository.read().distinctUntilChanged().collect { updateNotification(it) }
            }
        }
    }

    private fun updateNotification(progress: Int) {
        notificationBuilder
            .setProgress(100, progress, false)
            .setContentText("$progress%")
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        if (progress == 100) {
            stopSelf()
        }
    }
}
