package com.example.mysaaproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mysaaproject.data.notifications.NotificationsRepository
import com.example.mysaaproject.data.session.SessionRepository
import com.example.mysaaproject.ui.locale.LanguageRepository
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.navigation.AppNavHost
import com.example.mysaaproject.ui.navigation.Routes
import com.example.mysaaproject.ui.theme.SaaBackground
import kotlinx.coroutines.launch

/**
 * App entry composable: resolves the start destination from the persisted session, restores the
 * persisted in-app language, and hosts navigation. The language selection is saved via
 * [LanguageRepository] so it survives app restarts.
 */
@Composable
fun AppRoot() {
    val context = LocalContext.current
    val sessionRepository = remember { SessionRepository(context.applicationContext) }
    val languageRepository = remember { LanguageRepository(context.applicationContext) }
    val scope = rememberCoroutineScope()

    val isLoggedIn by sessionRepository.isLoggedInFlow.collectAsStateWithLifecycle(initialValue = null)
    val language by languageRepository.language.collectAsStateWithLifecycle(initialValue = null)
    val navController = rememberNavController()

    val loggedIn = isLoggedIn
    val currentLanguage = language
    // Wait until both the session and the persisted language have loaded — avoids a flash of the
    // wrong start screen / language on launch.
    if (loggedIn == null || currentLanguage == null) return

    ProvideAppLanguage(language = currentLanguage) {
        AppNavHost(
            navController = navController,
            startDestination = if (loggedIn) Routes.HOME else Routes.LOGIN,
            language = currentLanguage,
            onLanguageSelected = { scope.launch { languageRepository.setLanguage(it) } },
            onLogout = {
                scope.launch { sessionRepository.clearSession() }
                // Clear shared notification read-state so a re-login starts fresh. The language is a
                // device preference (not per-user), so it is intentionally kept across logout.
                NotificationsRepository.reset()
            },
            modifier = Modifier.fillMaxSize().background(SaaBackground),
        )
    }
}
