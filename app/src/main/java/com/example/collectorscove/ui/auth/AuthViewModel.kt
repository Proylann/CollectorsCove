package com.example.collectorscove.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.collectorscove.data.model.User
import com.example.collectorscove.data.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    init {
        authRepository.getCurrentUser()?.let { firebaseUser ->
            fetchUserProfile(firebaseUser.uid)
        }
    }

    fun fetchUserProfile(uid: String) {
        authRepository.getUserProfile(uid) { user ->
            _currentUser.value = user
        }
    }

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        address: String,
        nationality: String,
        gender: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        authRepository.registerUser(
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            address,
            nationality,
            gender
        ) { success, error ->
            if (success) {
                authRepository.getCurrentUser()?.let { firebaseUser ->
                    fetchUserProfile(firebaseUser.uid)
                }
                onResult(true, null)
            } else {
                onResult(false, error)
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        authRepository.loginUser(email, password) { success, error ->
            if (success) {
                authRepository.getCurrentUser()?.let { firebaseUser ->
                    fetchUserProfile(firebaseUser.uid)
                }
                onResult(true, null)
            } else {
                onResult(false, error)
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _currentUser.value = null
    }
}