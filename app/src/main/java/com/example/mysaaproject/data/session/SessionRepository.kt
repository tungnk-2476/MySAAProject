package com.example.mysaaproject.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

/**
 * Persists the authenticated session token via DataStore.
 * Backs auto-login, redirect-if-authenticated, and logout (TC_LOGIN_ACC_002 / FUN_012 / FUN_014).
 */
class SessionRepository(private val context: Context) {

    val authTokenFlow: Flow<String?> =
        context.sessionDataStore.data.map { it[KEY_AUTH_TOKEN] }

    val isLoggedInFlow: Flow<Boolean> =
        context.sessionDataStore.data.map { !it[KEY_AUTH_TOKEN].isNullOrEmpty() }

    suspend fun saveSession(token: String) {
        context.sessionDataStore.edit { it[KEY_AUTH_TOKEN] = token }
    }

    suspend fun clearSession() {
        context.sessionDataStore.edit { it.remove(KEY_AUTH_TOKEN) }
    }

    private companion object {
        val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
    }
}
