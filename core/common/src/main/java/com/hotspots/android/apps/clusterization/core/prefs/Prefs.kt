package com.hotspots.android.apps.clusterization.core.prefs

import android.content.Context

interface Prefs {
    var hotspotsLoaded: Boolean
    var hotspotsLastRow: Long
}

private const val PREFERENCES_FILE = "prefs.hotspots"
private const val PREF_HOTSPOTS_LOADED = "pref.hotspots_loaded"
private const val PREF_HOTSPOTS_LAST_ROW = "pref.hotspots_last_row"

class PrefsImpl(context: Context) : Prefs {
    private val preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)

    override var hotspotsLoaded: Boolean
        get() = preferences.getBoolean(PREF_HOTSPOTS_LOADED, false)
        set(value) = preferences.edit().putBoolean(PREF_HOTSPOTS_LOADED, value).apply()

    override var hotspotsLastRow: Long
        get() = preferences.getLong(PREF_HOTSPOTS_LAST_ROW, 0)
        set(value) = preferences.edit().putLong(PREF_HOTSPOTS_LAST_ROW, value).apply()
}
