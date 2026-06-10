package com.example.mysaaproject.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.ui.locale.AppLanguage

/**
 * Connects [LoginViewModel] to the presentational [LoginScreen]. Language state is owned at
 * the app level (so it survives navigation) and passed through.
 */
@Composable
fun LoginRoute(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreen(
        language = language,
        onLanguageSelected = onLanguageSelected,
        isLoading = uiState.isLoading,
        isError = uiState.isLoginError,
        onGoogleClick = { viewModel.onGoogleLoginClick(onLoginSuccess) },
    )
}
