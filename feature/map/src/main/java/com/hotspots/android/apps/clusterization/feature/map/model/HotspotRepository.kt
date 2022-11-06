package com.hotspots.android.apps.clusterization.feature.map.model

import com.hotspots.android.apps.clusterization.feature.map.data.entity.Hotspot

internal interface HotspotRepository {
    suspend fun getSources(
        leftBottomLat: Double,
        leftBottomLon: Double,
        rightTopLat: Double,
        rightTopLon: Double,
    ): List<Hotspot>
}
