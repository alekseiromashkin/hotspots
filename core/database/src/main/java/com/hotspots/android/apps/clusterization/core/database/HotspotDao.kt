package com.hotspots.android.apps.clusterization.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hotspots.android.apps.clusterization.core.database.model.HotspotPersistentEntity

@Dao
interface HotspotDao {
    @Query("SELECT * FROM hotspot WHERE lat BETWEEN :leftBottomLat AND :rightTopLat AND :leftBottomLon <= lon AND lon <= :rightTopLon LIMIT 10000")
    fun getHotspots1(
        leftBottomLat: Double,
        leftBottomLon: Double,
        rightTopLat: Double,
        rightTopLon: Double,
    ): List<HotspotPersistentEntity>

    @Query("SELECT * FROM hotspot WHERE lat BETWEEN :leftBottomLat AND :rightTopLat AND :leftBottomLon <= lon OR lon <= :rightTopLon LIMIT 10000")
    fun getHotspots2(
        leftBottomLat: Double,
        leftBottomLon: Double,
        rightTopLat: Double,
        rightTopLon: Double,
    ): List<HotspotPersistentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hotspots: Iterable<HotspotPersistentEntity>)

    @Delete
    fun delete(hotspot: HotspotPersistentEntity)
}
