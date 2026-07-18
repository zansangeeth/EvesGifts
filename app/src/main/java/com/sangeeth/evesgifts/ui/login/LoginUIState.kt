package com.sangeeth.evesgifts.ui.login

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseUser

sealed class LoginUIState {
    object Idle : LoginUIState()
    object Loading : LoginUIState()
    data class Success(val user : FirebaseUser): LoginUIState()
    data class Error(val message: String) : LoginUIState()
//    )
}