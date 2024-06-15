package com.vaxapp.thingspeakviewer.data.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun getSetting(key: String, defaultValue: Boolean = false): Boolean {
        return context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[booleanPreferencesKey(key)] ?: defaultValue
            }.first()
    }

    suspend fun setSetting(key: String, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }
}
