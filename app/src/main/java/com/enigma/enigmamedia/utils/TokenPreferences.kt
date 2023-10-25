package com.enigma.enigmamedia.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_preferences")

class TokenPreferences (context: Context) {

    private val TOKEN_AUTH_KEY = stringPreferencesKey("token_auth")
    private val dataStore = context.dataStore


    suspend fun saveToken(token: String) {
        dataStore.edit {
            preferences -> preferences[TOKEN_AUTH_KEY] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            preferences -> preferences[TOKEN_AUTH_KEY] ?: ""
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_AUTH_KEY)
        }
    }

}