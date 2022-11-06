package com.hotspots.android.apps.clusterization.feature.map.presentation

internal sealed class MapViewState
internal data class Show(val sourcesState: List<MapItem> = emptyList()): MapViewState()

