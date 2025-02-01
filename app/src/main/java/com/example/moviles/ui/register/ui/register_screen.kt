package com.example.moviles.ui.register.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scope = rememberCoroutineScope()
    val navigateToLogin by viewModel.navigateToLogin.collectAsState(initial = false)

    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            navController.navigate("login_screen") {
                popUpTo("register_screen") { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Register(Modifier.align(Alignment.Center), viewModel)
        }
    }
}


@Composable
fun Register(modifier: Modifier, viewModel: RegisterViewModel) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        NameField(viewModel)
        EmailField(viewModel)
        PasswordField(viewModel)
        ConfirmPasswordField(viewModel)
        RegisterButton(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(viewModel: RegisterViewModel) {
    val errorName = viewModel.errorName
    OutlinedTextField(
        value = viewModel.name,
        onValueChange = { viewModel.onNameChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nombre Completo", color = MaterialTheme.colorScheme.onBackground) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        isError = errorName != null,
        supportingText = {
            if (errorName != null) {
                Text(text = errorName, color = MaterialTheme.colorScheme.error)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(viewModel: RegisterViewModel) {
    val errorEmail = viewModel.errorEmail
    OutlinedTextField(
        value = viewModel.email,
        onValueChange = { viewModel.onEmailChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email", color = MaterialTheme.colorScheme.onBackground) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        isError = errorEmail != null,
        supportingText = {
            if (errorEmail != null) {
                Text(text = errorEmail, color = MaterialTheme.colorScheme.error)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
        ),
        shape = RoundedCornerShape(12.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(viewModel: RegisterViewModel) {
    var passwordVisible by remember { mutableStateOf(false) }
    val errorPassword = viewModel.errorPassword
    OutlinedTextField(
        value = viewModel.password,
        onValueChange = { viewModel.onPasswordChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Contraseña", color = MaterialTheme.colorScheme.onBackground) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = {passwordVisible = !passwordVisible}){

            }
        },
        isError = errorPassword != null,
        supportingText = {
            if (errorPassword != null) {
                Text(text = errorPassword, color = MaterialTheme.colorScheme.error)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
        ),
        shape = RoundedCornerShape(12.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPasswordField(viewModel: RegisterViewModel) {
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val errorConfirmPassword = viewModel.errorConfirmPassword
    OutlinedTextField(
        value = viewModel.confirmPassword,
        onValueChange = { viewModel.onConfirmPasswordChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Confirmar Contraseña", color = MaterialTheme.colorScheme.onBackground) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val description = if (confirmPasswordVisible) "Hide password" else "Show password"
            IconButton(onClick = {confirmPasswordVisible = !confirmPasswordVisible}){

            }
        },
        isError = errorConfirmPassword != null,
        supportingText = {
            if (errorConfirmPassword != null) {
                Text(text = errorConfirmPassword, color = MaterialTheme.colorScheme.error)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun RegisterButton(viewModel: RegisterViewModel) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch {
                viewModel.onRegisterButtonClicked()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = "Registrar", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}