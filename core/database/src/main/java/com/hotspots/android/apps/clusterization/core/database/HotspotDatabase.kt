package com.hotspots.android.apps.clusterization.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hotspots.android.apps.clusterization.core.database.model.HotspotPersistentEntity

@Database(entities = [HotspotPersistentEntity::class], version = 1)
abstract class HotspotDatabase : RoomDatabase() {
    abstract fun hotspotDao(): HotspotDao
}

