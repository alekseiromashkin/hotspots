package com.hotspots.android.apps.clusterization.app

import android.content.Context
import androidx.room.Room
import com.hotspots.android.apps.clusterization.core.database.HotspotDatabase

internal fun buildDatabase(applicationContext: Context): HotspotDatabase {
    return Room.databaseBuilder(
        applicationContext,
        HotspotDatabase::class.java, "news.db"
    ).build()
}
