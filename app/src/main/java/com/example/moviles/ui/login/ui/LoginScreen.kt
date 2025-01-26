package com.example.moviles.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import com.example.moviles.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavHostController = rememberNavController()) {
    val scope = rememberCoroutineScope()
    val navigateToHome by viewModel.navigateToHome.collectAsState(initial = false)
    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            navController.navigate("home_screen") // Navegar a la otra vista
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
            Login(Modifier.align(Alignment.Center), viewModel)
        }
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderImage(Modifier.padding(bottom = 32.dp))
        EmailField(viewModel)
        PasswordField(viewModel)
        LoginButton(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(viewModel: LoginViewModel) {
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
fun PasswordField(viewModel: LoginViewModel) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = viewModel.password,
        onValueChange = { viewModel.onPasswordChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Password", color = MaterialTheme.colorScheme.onBackground) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {



            // Localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){

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
fun LoginButton(viewModel: LoginViewModel) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch {
                viewModel.onLoginButtonClicked()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = "Iniciar sesión", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Header",
        modifier = modifier,

        )
}