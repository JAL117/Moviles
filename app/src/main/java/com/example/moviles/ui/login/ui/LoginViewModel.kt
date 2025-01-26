package com.example.moviles.ui.login.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginEnabled by mutableStateOf(false)
        private set

    private val _navigateToHome = MutableSharedFlow<Boolean>()
    val navigateToHome: SharedFlow<Boolean> = _navigateToHome.asSharedFlow()

    var errorEmail by mutableStateOf<String?>(null)
        private set

    fun onEmailChanged(newEmail: String) {
        email = newEmail
        validateEmail()
        validateLogin()

    }


    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        validateLogin()
    }

    private fun validateEmail() {
        errorEmail = if (email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Email no válido"
        } else {
            null
        }
    }

    private fun validateLogin() {
        loginEnabled = email.isNotBlank() && password.isNotBlank() && errorEmail == null
    }

    suspend fun onLoginButtonClicked() {
        // Simulación de la lógica de inicio de sesión
        if (email == "jal@117.com" && password == "123456") {
            // Si es exitoso, se emite evento de navegación
            _navigateToHome.emit(true)
        } else {
            //Manejo de error
            println("Inicio de sesión fallido")
        }
    }
}