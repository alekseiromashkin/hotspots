package com.hotspots.android.apps.clusterization.feature.readservice.data

import android.content.res.Resources
import com.hotspots.android.apps.clusterization.core.database.HotspotDatabase
import com.hotspots.android.apps.clusterization.core.database.model.HotspotPersistentEntity
import com.hotspots.android.apps.clusterization.core.prefs.Prefs
import com.hotspots.android.apps.clusterization.feature.map.R
import com.hotspots.android.apps.clusterization.feature.readservice.model.HotspotsReaderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

const val CSV_LINES_COUNT = 1_500_000
const val BUFFER_SIZE = 1000

internal class HotspotsReaderRepositoryImpl @Inject constructor(
    private val resources: Resources,
    private val database: HotspotDatabase,
    private val prefs: Prefs,
) : HotspotsReaderRepository {

    override suspend fun read(): Flow<Int> = flow {
        if (!prefs.hotspotsLoaded) {
            val lastRow = prefs.hotspotsLastRow
            val inputStream = resources.openRawResource(R.raw.hotspots)
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.use {
                emit(0)
                var lineCounter = 0
                val inputBuffer = mutableListOf<HotspotPersistentEntity>()
                while (reader.ready()) {
                    if (lastRow <= lineCounter) {
                        val line: String = reader.readLine()
                        val elements = line.split(",").map { it.trim() }

                        val id = elements[1]
                        if (id.isNotBlank()) {
                            runCatching {
                                val lat = elements[2].ifBlank { "0" }.toDouble()
                                val lon = elements[3].ifBlank { "0" }.toDouble()
                                inputBuffer.add(HotspotPersistentEntity(id.toLong(), lat, lon))
                            }
                        }
                        if(inputBuffer.size == BUFFER_SIZE) {
                            database.hotspotDao().insert(inputBuffer)
                            inputBuffer.clear()
                            prefs.hotspotsLastRow = (lineCounter).toLong()
                            emit((100 * lineCounter) / CSV_LINES_COUNT)
                        }
                    }
                    lineCounter++
                }
                if(inputBuffer.size > 0) {
                    database.hotspotDao().insert(inputBuffer)
                    inputBuffer.clear()
                    prefs.hotspotsLastRow = (lineCounter).toLong()
                }
            }
        }
        prefs.hotspotsLoaded = true
        emit(100)
    }
}
