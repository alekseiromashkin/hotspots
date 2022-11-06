package com.hotspots.android.apps.clusterization.feature.readservice.model

import kotlinx.coroutines.flow.Flow

internal interface HotspotsReaderRepository {
    suspend fun read(): Flow<Int>
}