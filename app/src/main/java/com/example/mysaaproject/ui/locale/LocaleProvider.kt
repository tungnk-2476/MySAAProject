package com.example.mysaaproject.ui.locale

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

/** Current in-app language. */
val LocalAppLanguage = compositionLocalOf { AppLanguage.DEFAULT }

/** Context whose resources resolve to [LocalAppLanguage] — read localized strings from here. */
val LocalLocalizedContext = compositionLocalOf<Context> {
    error("LocalLocalizedContext not provided. Wrap content in ProvideAppLanguage.")
}

/**
 * Provides a localized [Context] for [language] so UI text re-renders immediately when the
 * language changes — no Activity recreation required (matches TC_LOGIN_FUN_004).
 */
@Composable
fun ProvideAppLanguage(language: AppLanguage, content: @Composable () -> Unit) {
    val baseContext = LocalContext.current
    val localizedContext = remember(language, baseContext) {
        val config = Configuration(baseContext.resources.configuration)
        config.setLocale(Locale.forLanguageTag(language.localeTag))
        baseContext.createConfigurationContext(config)
    }
    CompositionLocalProvider(
        LocalAppLanguage provides language,
        LocalLocalizedContext provides localizedContext,
        content = content,
    )
}

/** Localized string lookup that recomposes when the app language changes. */
@Composable
fun appString(resId: Int): String = LocalLocalizedContext.current.getString(resId)

/** Localized string-array lookup that recomposes when the app language changes. */
@Composable
fun appStringArray(resId: Int): List<String> =
    LocalLocalizedContext.current.resources.getStringArray(resId).toList()
