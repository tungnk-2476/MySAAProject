package com.example.mysaaproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mysaaproject.ui.awards.AwardRoute
import com.example.mysaaproject.ui.home.HomeRoute
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.login.LoginRoute
import com.example.mysaaproject.ui.kudos.AllKudosRoute
import com.example.mysaaproject.ui.kudos.KudosRoute
import com.example.mysaaproject.ui.kudos.SearchSunnerRoute
import com.example.mysaaproject.ui.kudos.SendKudoRoute
import com.example.mysaaproject.ui.kudos.ViewKudoRoute
import com.example.mysaaproject.ui.kudos.ViewKudoViewModel
import com.example.mysaaproject.ui.notifications.NotificationsRoute
import com.example.mysaaproject.ui.standards.CommunityStandardsScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"
    const val COMMUNITY_STANDARDS = "community_standards"
    const val KUDOS = "kudos"
    const val KUDOS_ALL = "kudos_all"
    const val KUDOS_VIEW = "kudos_view"
    const val SEND_KUDO = "send_kudo"
    const val KUDOS_SEARCH = "kudos_search"
    const val AWARDS = "awards"

    /** Build the View Kudo detail route for a specific kudo id. */
    fun kudosView(id: String) = "$KUDOS_VIEW/$id"
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
                onOpenKudos = {
                    navController.navigate(Routes.KUDOS) { launchSingleTop = true }
                },
                onOpenAwards = {
                    navController.navigate(Routes.AWARDS) { launchSingleTop = true }
                },
            )
        }
        composable(Routes.AWARDS) {
            AwardRoute(
                language = language,
                onLanguageSelected = onLanguageSelected,
                onOpenSearch = {
                    navController.navigate(Routes.KUDOS_SEARCH) { launchSingleTop = true }
                },
                onOpenNotifications = {
                    navController.navigate(Routes.NOTIFICATIONS) { launchSingleTop = true }
                },
                onOpenKudos = {
                    navController.navigate(Routes.KUDOS) { launchSingleTop = true }
                },
                onSaaTab = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onProfileTab = { /* TODO: Profile screen */ },
            )
        }
        composable(Routes.KUDOS) {
            KudosRoute(
                language = language,
                onLanguageSelected = onLanguageSelected,
                onOpenNotifications = {
                    navController.navigate(Routes.NOTIFICATIONS) { launchSingleTop = true }
                },
                onSaaTab = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onAwardsTab = {
                    navController.navigate(Routes.AWARDS) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProfileTab = { /* TODO: Profile screen */ },
                onOpenAllKudos = {
                    navController.navigate(Routes.KUDOS_ALL) { launchSingleTop = true }
                },
                onOpenKudo = { id ->
                    navController.navigate(Routes.kudosView(id)) { launchSingleTop = true }
                },
                onSendKudo = {
                    navController.navigate(Routes.SEND_KUDO) { launchSingleTop = true }
                },
                onOpenSearch = {
                    navController.navigate(Routes.KUDOS_SEARCH) { launchSingleTop = true }
                },
            )
        }
        composable(Routes.KUDOS_SEARCH) {
            SearchSunnerRoute(
                onBack = { navController.popBackStack() },
                onSaaTab = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onKudosTab = {
                    navController.navigate(Routes.KUDOS) {
                        popUpTo(Routes.KUDOS) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onAwardsTab = {
                    navController.navigate(Routes.AWARDS) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProfileTab = { /* TODO: Profile screen */ },
            )
        }
        composable(Routes.KUDOS_ALL) {
            AllKudosRoute(
                onBack = { navController.popBackStack() },
                onSaaTab = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onKudosTab = {
                    navController.navigate(Routes.KUDOS) {
                        popUpTo(Routes.KUDOS) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onAwardsTab = {
                    navController.navigate(Routes.AWARDS) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProfileTab = { /* TODO: Profile screen */ },
                onOpenKudo = { id ->
                    navController.navigate(Routes.kudosView(id)) { launchSingleTop = true }
                },
            )
        }
        composable(Routes.SEND_KUDO) {
            SendKudoRoute(
                onClose = { navController.popBackStack() },
                onSaaTab = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onKudosTab = {
                    navController.navigate(Routes.KUDOS) {
                        popUpTo(Routes.KUDOS) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onAwardsTab = {
                    navController.navigate(Routes.AWARDS) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProfileTab = { /* TODO: Profile screen */ },
            )
        }
        composable(
            route = "${Routes.KUDOS_VIEW}/{${ViewKudoViewModel.KUDO_ID_ARG}}",
            arguments = listOf(navArgument(ViewKudoViewModel.KUDO_ID_ARG) { type = NavType.StringType }),
        ) {
            ViewKudoRoute(
                onBack = { navController.popBackStack() },
                onSaaTab = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onKudosTab = {
                    navController.navigate(Routes.KUDOS) {
                        popUpTo(Routes.KUDOS) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onAwardsTab = {
                    navController.navigate(Routes.AWARDS) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProfileTab = { /* TODO: Profile screen */ },
            )
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsRoute(
                onBack = { navController.popBackStack() },
                onOpenCommunityStandards = {
                    navController.navigate(Routes.COMMUNITY_STANDARDS) { launchSingleTop = true }
                },
            )
        }
        composable(Routes.COMMUNITY_STANDARDS) {
            CommunityStandardsScreen(onBack = { navController.popBackStack() })
        }
    }
}
