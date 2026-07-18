package com.sangeeth.evesgifts.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {

        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()
        if (trimmedEmail.isBlank() || trimmedPassword.isBlank()) {
            _uiState.value = LoginUIState.Error("Email ad password cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUIState.Loading
            try {
                val result=  auth.signInWithEmailAndPassword(trimmedEmail, trimmedPassword).await()
                val user = result.user
                if (user != null){
                    _uiState.value = LoginUIState.Success(user)
                } else{
                    _uiState.value= LoginUIState.Error("Login Failed")
                }
            } catch (e: Exception){
                _uiState.value = LoginUIState.Error(e.message ?: "login error")
            }
        }

    }
    fun resetState(){
        _uiState.value = LoginUIState.Idle
    }
}