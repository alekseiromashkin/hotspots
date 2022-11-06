package com.hotspots.android.apps.clusterization.feature.map.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.hotspots.android.apps.clusterization.core.viewmodel.lazyViewModel
import com.hotspots.android.apps.clusterization.feature.map.di.DaggerMapComponent
import com.hotspots.android.apps.clusterization.feature.map.di.MapComponent
import com.hotspots.android.apps.clusterization.feature.map.di.MapDependenciesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MapFragment : SupportMapFragment() {

    private lateinit var sourcesComponent: MapComponent

    private lateinit var map: GoogleMap

    private var clusterManager: ClusterManager<MapItem>? = null

    private val viewModel: MapViewModel by lazyViewModel {
        sourcesComponent.sourcesViewModelFactory().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sourcesComponent = DaggerMapComponent
            .builder()
            .dependencies(MapDependenciesProvider.dependencies)
            .build()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        collectViewState()
    }

    private fun initViews() {
        getMapAsync { map ->
            this.map = map

            clusterManager = ClusterManager(context, map)
            context?.let {
                clusterManager?.renderer = MarkerClusterRenderer(it, map, clusterManager)
            }

            clusterManager?.setOnClusterItemClickListener {
                val update = CameraUpdateFactory.newLatLng(it.position)
                map.moveCamera(update)
                true
            }

            map.setOnCameraIdleListener {
                viewModel.onMapPositionChanged(map.projection.visibleRegion)
            }
        }
    }

    private fun collectViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.viewState.collectLatest { render(it) }
                }
            }
        }
    }

    private fun render(state: MapViewState) {
        when(state) {
            is Show -> {
                clusterManager?.clearItems()
                for (hotspot in state.sourcesState) {
                    clusterManager?.addItem(hotspot)
                }
                clusterManager?.cluster()
            }
        }
    }
}
