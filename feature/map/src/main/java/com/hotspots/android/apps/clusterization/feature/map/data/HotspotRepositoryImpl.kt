package com.hotspots.android.apps.clusterization.feature.map.data

import com.hotspots.android.apps.clusterization.core.database.HotspotDatabase
import com.hotspots.android.apps.clusterization.core.database.model.HotspotPersistentEntity
import com.hotspots.android.apps.clusterization.feature.map.data.entity.Hotspot
import com.hotspots.android.apps.clusterization.feature.map.model.HotspotRepository
import javax.inject.Inject

internal class HotspotRepositoryImpl @Inject constructor(
    private val database: HotspotDatabase,
) : HotspotRepository {

    override suspend fun getSources(
        leftBottomLat: Double,
        leftBottomLon: Double,
        rightTopLat: Double,
        rightTopLon: Double
    ): List<Hotspot> {
        val dto = database.hotspotDao()
        val result = if (leftBottomLon <= rightTopLon) {
            dto.getHotspots1(leftBottomLat, leftBottomLon, rightTopLat, rightTopLon)
        } else {
            dto.getHotspots2(leftBottomLat, leftBottomLon, rightTopLat, rightTopLon)
        }

        return result.map { it.toHotspot() }
    }
}

private fun HotspotPersistentEntity.toHotspot(): Hotspot {
    return Hotspot(
        id = id,
        lat = lat,
        lon = lon,
    )
}
