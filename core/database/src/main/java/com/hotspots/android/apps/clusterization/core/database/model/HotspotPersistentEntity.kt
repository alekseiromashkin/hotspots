package com.hotspots.android.apps.clusterization.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotspot")
data class HotspotPersistentEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
)
