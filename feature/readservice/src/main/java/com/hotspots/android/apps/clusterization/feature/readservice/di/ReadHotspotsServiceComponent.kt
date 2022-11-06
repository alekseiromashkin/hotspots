package com.hotspots.android.apps.clusterization.feature.readservice.di

import android.content.res.Resources
import androidx.annotation.RestrictTo
import com.hotspots.android.apps.clusterization.core.database.HotspotDatabase
import com.hotspots.android.apps.clusterization.core.di.FeatureScope
import com.hotspots.android.apps.clusterization.core.prefs.Prefs
import com.hotspots.android.apps.clusterization.feature.readservice.presentation.ReadHotspotsService
import dagger.Component
import kotlin.properties.Delegates.notNull

@Component(
    modules = [ReadServiceModule::class],
    dependencies = [ReadHotspotsServiceDependencies::class]
)
@FeatureScope
internal interface ReadHotspotsServiceComponent {

    fun inject(readHotspotsService: ReadHotspotsService)

    @Component.Builder
    interface Builder {
        fun dependencies(sourcesDependencies: ReadHotspotsServiceDependencies): Builder

        fun build(): ReadHotspotsServiceComponent
    }
}

interface ReadHotspotsServiceDependencies {
    val database: HotspotDatabase
    val resources: Resources
    val prefs: Prefs
}

interface ReadHotspotsServiceDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: ReadHotspotsServiceDependencies

    companion object :
        ReadHotspotsServiceDependenciesProvider by ReadHotspotsServiceDependenciesStore
}

object ReadHotspotsServiceDependenciesStore : ReadHotspotsServiceDependenciesProvider {

    override var dependencies: ReadHotspotsServiceDependencies by notNull()
}
