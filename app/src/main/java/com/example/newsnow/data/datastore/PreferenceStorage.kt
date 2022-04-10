package com.example.newsnow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.example.newsnow.utils.Constants.BREAKING
import com.example.newsnow.utils.Constants.USER_PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorage {

    suspend fun setCurrentQuery(query: String)

    suspend fun getCurrentQuery() : Flow<String>

}

@Singleton
class AppPreferenceStorage
@Inject constructor(@ApplicationContext private val context: Context) : PreferenceStorage {

    companion object {
        val CURRENT_QUERY = stringPreferencesKey("QUERY")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun setCurrentQuery(query: String) {
        context.dataStore.edit {
            it[CURRENT_QUERY] = query
        }
    }

    override suspend fun getCurrentQuery(): Flow<String> = context.dataStore.data.map {
        it[CURRENT_QUERY] ?: BREAKING
    }

}


