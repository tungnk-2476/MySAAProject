package com.example.mysaaproject.data.auth

import kotlinx.coroutines.delay

/**
 * Stubbed Google authentication.
 *
 * Real Google OAuth (Credential Manager + Google ID) is intentionally deferred — see
 * plans/.../clarifications.md. This simulates the auth round-trip so the login flow,
 * loading state, double-click guard, and navigation are fully exercisable end-to-end.
 */
class AuthRepository {

    /** Simulates the Google OAuth round-trip and returns a session token on success. */
    suspend fun signInWithGoogle(): Result<String> {
        delay(SIMULATED_AUTH_DELAY_MS)
        return Result.success("stub-google-token")
    }

    private companion object {
        const val SIMULATED_AUTH_DELAY_MS = 1500L
    }
}
