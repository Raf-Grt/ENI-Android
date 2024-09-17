package fr.eni.eni_shop.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context : Context) {

    val Context.dataStore by preferencesDataStore(name = "user_preferences")
    val DARK_THEME = booleanPreferencesKey("dark_theme");

    val darkModeSettings: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME] ?: false
        }

    suspend fun saveDarkThemeSettings(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME] = isEnabled
        }
    }
}
