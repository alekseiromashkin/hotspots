package com.hotspots.android.apps.clusterization.feature.map.di

import com.hotspots.android.apps.clusterization.core.di.FeatureScope
import com.hotspots.android.apps.clusterization.feature.map.data.HotspotRepositoryImpl
import com.hotspots.android.apps.clusterization.feature.map.model.HotspotRepository
import dagger.Binds
import dagger.Module

@Module
internal interface MapModule {

    @Binds
    @FeatureScope
    fun sourcesRepository(sourcesRepository: HotspotRepositoryImpl): HotspotRepository
}
