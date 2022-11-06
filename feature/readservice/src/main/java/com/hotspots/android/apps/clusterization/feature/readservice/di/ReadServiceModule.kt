package com.hotspots.android.apps.clusterization.feature.readservice.di

import com.hotspots.android.apps.clusterization.core.di.FeatureScope
import com.hotspots.android.apps.clusterization.feature.readservice.data.HotspotsReaderRepositoryImpl
import com.hotspots.android.apps.clusterization.feature.readservice.model.HotspotsReaderRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ReadServiceModule {

    @Binds
    @FeatureScope
    fun hotspotsReaderRepository(
        hotspotsReaderRepository: HotspotsReaderRepositoryImpl
    ): HotspotsReaderRepository
}
