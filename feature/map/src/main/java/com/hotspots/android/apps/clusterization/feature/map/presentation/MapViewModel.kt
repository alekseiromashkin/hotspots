package com.hotspots.android.apps.clusterization.feature.map.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.VisibleRegion
import com.hotspots.android.apps.clusterization.feature.map.data.entity.Hotspot
import com.hotspots.android.apps.clusterization.feature.map.model.HotspotRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class MapViewModel @AssistedInject constructor(
    private val hotspots: HotspotRepository,
) : ViewModel() {

    private val _viewState: MutableStateFlow<MapViewState> = MutableStateFlow(Show())
    val viewState: StateFlow<MapViewState> = _viewState.asStateFlow()

    fun onMapPositionChanged(visibleRegion: VisibleRegion) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val hotspots = hotspots.getSources(
                    leftBottomLat = visibleRegion.nearLeft.latitude,
                    leftBottomLon = visibleRegion.nearLeft.longitude,
                    rightTopLat = visibleRegion.farRight.latitude,
                    rightTopLon = visibleRegion.farRight.longitude,
                )
                _viewState.update { Show(hotspots.map { it.toMapItem() }) }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MapViewModel
    }
}

private fun Hotspot.toMapItem(): MapItem {
    return MapItem(LatLng(lat, lon))
}