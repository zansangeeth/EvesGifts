package com.sangeeth.evesgifts.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUIState>(ProfileUIState.Loading)
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    private val _logoutEvent = MutableStateFlow(false)
    val logoutEvent: StateFlow<Boolean> = _logoutEvent.asStateFlow()


    private val authStateListener = FirebaseAuth.AuthStateListener{ firebaseAuth ->
        val user  = firebaseAuth.currentUser
        loadUserProfile(user)
    }
    init {
        auth.addAuthStateListener(authStateListener)
        loadUserProfile(auth.currentUser)
    }

    private fun loadUserProfile(user: FirebaseUser?) {
        if (user != null){
            _uiState.value = ProfileUIState.Success(
                diaplayName = user.email?.substringBefore('@')?.takeIf { it.isNotBlank() } ?: "No Name",
                email = user.email ?: "No Email"
            )
        }else{
            _uiState.value = ProfileUIState.Error("User not logged in")
        }
    }

    fun logout(){
        viewModelScope.launch {
            auth.signOut()
            _logoutEvent.value = true
        }
    }

    fun resetLogoutEvent(){
        _logoutEvent.value = false
    }
}