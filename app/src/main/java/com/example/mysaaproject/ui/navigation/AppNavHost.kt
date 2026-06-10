package com.example.mysaaproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysaaproject.ui.home.HomeRoute
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.login.LoginRoute
import com.example.mysaaproject.ui.notifications.NotificationsRoute

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"
}

/**
 * App navigation graph. [startDestination] is decided by the persisted session
 * (auto-login / redirect-if-authenticated, TC_LOGIN_ACC_002).
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Routes.LOGIN) {
            LoginRoute(
                language = language,
                onLanguageSelected = onLanguageSelected,
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
            )
        }
        composable(Routes.HOME) {
            HomeRoute(
                language = language,
                onLanguageSelected = onLanguageSelected,
                onLogout = {
                    onLogout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onOpenNotifications = {
                    navController.navigate(Routes.NOTIFICATIONS) { launchSingleTop = true }
                },
            )
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsRoute(onBack = { navController.popBackStack() })
        }
    }
}
