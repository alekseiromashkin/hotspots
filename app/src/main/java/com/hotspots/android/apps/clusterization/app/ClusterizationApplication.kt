package com.hotspots.android.apps.clusterization.app

import android.app.Application
import com.hotspots.android.apps.clusterization.app.di.ApplicationComponent
import com.hotspots.android.apps.clusterization.app.di.DaggerApplicationComponent
import com.hotspots.android.apps.clusterization.feature.map.di.MapDependenciesStore
import com.hotspots.android.apps.clusterization.feature.readservice.di.ReadHotspotsServiceDependenciesStore

class ClusterizationApplication : Application() {

    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .database(buildDatabase(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        initDependencies()
    }

    private fun initDependencies() {
        MapDependenciesStore.dependencies = applicationComponent
        ReadHotspotsServiceDependenciesStore.dependencies = applicationComponent
    }
}
