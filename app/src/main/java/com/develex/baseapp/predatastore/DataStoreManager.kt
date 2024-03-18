package com.develex.baseapp.predatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val APP_DATASTORE = "app_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE)

/*
    Local data class to keep track of local settings. this needs to be reflected to the ViewModel on load and change
 */
data class Settings(
    val darkMode: Boolean = false,
    val autoMode: Boolean = false
)

class DataStoreManager(val context: Context) {
    //    set all the keys that are needed to be store in the datastore
    companion object {
        val DARK_MODE = booleanPreferencesKey("DARK_MODE")
        val AUTO_MODE = booleanPreferencesKey("AUTO_MODE")
    }

    suspend fun saveToDataStore(settings: Settings) {
        context.dataStore.edit {
            it[DARK_MODE] = settings.darkMode
            it[AUTO_MODE] = settings.autoMode
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        Settings(
            darkMode = it[DARK_MODE] ?: false,
            autoMode = it[AUTO_MODE] ?: false
        )
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }
}