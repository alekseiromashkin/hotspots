package com.hotspots.android.apps.clusterization.app.di

import android.content.Context
import android.content.res.Resources
import com.hotspots.android.apps.clusterization.core.database.HotspotDatabase
import com.hotspots.android.apps.clusterization.core.di.ApplicationContext
import com.hotspots.android.apps.clusterization.core.prefs.Prefs
import com.hotspots.android.apps.clusterization.feature.map.di.MapDependencies
import com.hotspots.android.apps.clusterization.feature.readservice.di.ReadHotspotsServiceDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
internal interface ApplicationComponent : MapDependencies, ReadHotspotsServiceDependencies {

    override val database: HotspotDatabase

    override val resources: Resources

    override val prefs: Prefs

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@ApplicationContext applicationContext: Context): Builder

        @BindsInstance
        fun database(database: HotspotDatabase) : Builder

        fun build(): ApplicationComponent
    }
}
