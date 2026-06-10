package com.example.mysaaproject

import com.example.mysaaproject.data.auth.AuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthRepositoryTest {

    @Test
    fun signInWithGoogle_returnsSuccessfulTokenResult() = runBlocking {
        val result = AuthRepository().signInWithGoogle()
        assertTrue("stubbed sign-in should succeed", result.isSuccess)
        assertTrue("token should be non-empty", !result.getOrNull().isNullOrEmpty())
    }
}
