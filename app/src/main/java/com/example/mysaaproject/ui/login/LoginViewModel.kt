package com.example.mysaaproject.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysaaproject.data.auth.AuthRepository
import com.example.mysaaproject.data.session.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoginError: Boolean = false,
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val sessionRepository = SessionRepository(application)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Starts the (stubbed) Google sign-in. The [LoginUiState.isLoading] guard prevents
     * duplicate auth requests on rapid double-clicks (TC_LOGIN_FUN_008).
     */
    fun onGoogleLoginClick(onSuccess: () -> Unit) {
        if (_uiState.value.isLoading) return
        _uiState.update { it.copy(isLoading = true, isLoginError = false) }
        viewModelScope.launch {
            authRepository.signInWithGoogle()
                .onSuccess { token ->
                    sessionRepository.saveSession(token)
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false, isLoginError = true) }
                }
        }
    }
}
