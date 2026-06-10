package com.example.mysaaproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mysaaproject.data.notifications.NotificationsRepository
import com.example.mysaaproject.data.session.SessionRepository
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.navigation.AppNavHost
import com.example.mysaaproject.ui.navigation.Routes
import com.example.mysaaproject.ui.theme.SaaBackground
import kotlinx.coroutines.launch

/**
 * App entry composable: resolves the start destination from the persisted session,
 * owns the in-app language state, and hosts navigation.
 */
@Composable
fun AppRoot() {
    val context = LocalContext.current
    val sessionRepository = remember { SessionRepository(context.applicationContext) }
    val scope = rememberCoroutineScope()

    var language by rememberSaveable { mutableStateOf(AppLanguage.DEFAULT) }
    val isLoggedIn by sessionRepository.isLoggedInFlow.collectAsStateWithLifecycle(initialValue = null)
    val navController = rememberNavController()

    ProvideAppLanguage(language = language) {
        when (isLoggedIn) {
            // Session state still loading — render nothing briefly to avoid a flash.
            null -> Unit
            else -> AppNavHost(
                navController = navController,
                startDestination = if (isLoggedIn == true) Routes.HOME else Routes.LOGIN,
                language = language,
                onLanguageSelected = { language = it },
                onLogout = {
                    scope.launch { sessionRepository.clearSession() }
                    // Clear shared notification read-state so a re-login starts fresh.
                    NotificationsRepository.reset()
                },
                modifier = Modifier.fillMaxSize().background(SaaBackground),
            )
        }
    }
}
