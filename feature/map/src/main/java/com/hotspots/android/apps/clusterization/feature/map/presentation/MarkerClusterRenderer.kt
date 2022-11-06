package com.hotspots.android.apps.clusterization.feature.map.presentation

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.hotspots.android.apps.clusterization.core.getBitmapFromVectorDrawable
import com.hotspots.android.apps.clusterization.feature.map.R

const val CLUSTER_LARGE = 1000
const val CLUSTER_BIG = 500
const val CLUSTER_MEDIUM = 100
const val CLUSTER_SMALL = 20

class MarkerClusterRenderer<T : ClusterItem?>(
    context: Context,
    googleMap: GoogleMap?,
    clusterManager: ClusterManager<T>?
) :
    DefaultClusterRenderer<T>(context, googleMap, clusterManager) {

    private val iconClusterLarge: BitmapDescriptor
    private val iconClusterBig: BitmapDescriptor
    private val iconClusterMedium: BitmapDescriptor
    private val iconClusterSmall: BitmapDescriptor

    init {
        val bitmapClusterLarge = getBitmapFromVectorDrawable(context, R.drawable.ic_cluster_large)
        iconClusterLarge = BitmapDescriptorFactory.fromBitmap(bitmapClusterLarge)

        val bitmapClusterBig = getBitmapFromVectorDrawable(context, R.drawable.ic_cluster_big)
        iconClusterBig = BitmapDescriptorFactory.fromBitmap(bitmapClusterBig)

        val bitmapClusterMedium = getBitmapFromVectorDrawable(context, R.drawable.ic_cluster_medium)
        iconClusterMedium = BitmapDescriptorFactory.fromBitmap(bitmapClusterMedium)

        val bitmapClusterSmall = getBitmapFromVectorDrawable(context, R.drawable.ic_cluster_small)
        iconClusterSmall = BitmapDescriptorFactory.fromBitmap(bitmapClusterSmall)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<T>, markerOptions: MarkerOptions) {
        when {
            cluster.size >= CLUSTER_LARGE -> markerOptions.icon(iconClusterLarge)
            cluster.size >= CLUSTER_BIG -> markerOptions.icon(iconClusterBig)
            cluster.size >= CLUSTER_MEDIUM -> markerOptions.icon(iconClusterMedium)
            cluster.size >= CLUSTER_SMALL -> markerOptions.icon(iconClusterSmall)
            else -> super.onBeforeClusterRendered(cluster, markerOptions)
        }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<T>): Boolean {
        return cluster.size >= CLUSTER_SMALL
    }
}
