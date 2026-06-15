package com.example.mysaaproject.ui.locale

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Persists the in-app language (VN/EN) via DataStore so the choice survives app restarts — mirrors
 * [com.example.mysaaproject.data.session.SessionRepository]. Until the user picks one, [language]
 * emits [AppLanguage.DEFAULT].
 */
class LanguageRepository(private val context: Context) {

    /** Current language; defaults to [AppLanguage.DEFAULT] when nothing has been saved yet. */
    val language: Flow<AppLanguage> =
        context.settingsDataStore.data.map { prefs -> AppLanguage.fromTag(prefs[KEY_LANGUAGE]) }

    suspend fun setLanguage(language: AppLanguage) {
        context.settingsDataStore.edit { it[KEY_LANGUAGE] = language.localeTag }
    }

    private companion object {
        val KEY_LANGUAGE = stringPreferencesKey("app_language")
    }
}
