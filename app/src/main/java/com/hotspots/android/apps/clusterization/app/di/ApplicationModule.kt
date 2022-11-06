package com.hotspots.android.apps.clusterization.app.di

import android.content.Context
import android.content.res.Resources
import com.hotspots.android.apps.clusterization.core.di.ApplicationContext
import com.hotspots.android.apps.clusterization.core.prefs.Prefs
import com.hotspots.android.apps.clusterization.core.prefs.PrefsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class ApplicationModule {

    @Provides
    @Singleton
    fun resources(@ApplicationContext applicationContext: Context): Resources {
        return applicationContext.resources
    }

    @Provides
    @Singleton
    fun prefs(@ApplicationContext applicationContext: Context): Prefs {
        return PrefsImpl(applicationContext)
    }
}
