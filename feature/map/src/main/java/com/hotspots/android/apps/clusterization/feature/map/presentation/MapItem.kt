package com.hotspots.android.apps.clusterization.feature.map.presentation

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

internal class MapItem(
    private val mPosition: LatLng
) : ClusterItem {
    override fun getPosition() = mPosition
    override fun getTitle() = ""
    override fun getSnippet() = ""
}
