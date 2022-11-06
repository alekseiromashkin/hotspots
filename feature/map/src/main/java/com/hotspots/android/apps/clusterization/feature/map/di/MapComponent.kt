package com.hotspots.android.apps.clusterization.feature.map.di

import android.content.res.Resources
import androidx.annotation.RestrictTo
import com.hotspots.android.apps.clusterization.core.database.HotspotDatabase
import com.hotspots.android.apps.clusterization.core.di.FeatureScope
import com.hotspots.android.apps.clusterization.feature.map.presentation.MapViewModel
import dagger.Component
import kotlin.properties.Delegates.notNull

@Component(
    modules = [MapModule::class],
    dependencies = [MapDependencies::class]
)
@FeatureScope
internal interface MapComponent {

    fun sourcesViewModelFactory(): MapViewModel.Factory

    @Component.Builder
    interface Builder {
        fun dependencies(sourcesDependencies: MapDependencies): Builder

        fun build(): MapComponent
    }
}

interface MapDependencies {
    val database: HotspotDatabase
    val resources: Resources
}

interface MapDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: MapDependencies

    companion object : MapDependenciesProvider by MapDependenciesStore
}

object MapDependenciesStore : MapDependenciesProvider {

    override var dependencies: MapDependencies by notNull()
}