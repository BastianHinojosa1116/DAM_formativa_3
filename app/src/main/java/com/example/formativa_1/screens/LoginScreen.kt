package com.example.formativa_1.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.formativa_1.data.UserDataStore
import com.airbnb.lottie.compose.*


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    onNavigate: (String) -> Unit,
    userPrefs: UserDataStore
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    val storedUsername by userPrefs.getUsername.collectAsState(initial = "")
    val storedPassword by userPrefs.getPassword.collectAsState(initial = "")

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url("https://assets2.lottiefiles.com/packages/lf20_5ngs2ksb.json")
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            if (composition == null) {
                CircularProgressIndicator()
            } else {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = {
                usuario = it
                error = false
            },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                error = false
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                error = !(usuario == storedUsername && contrasena == storedPassword)
                if (!error) onNavigate("home")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        if (error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Credenciales incorrectas",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { onNavigate("register") }) {
            Text("¿No tienes cuenta? Regístrate")
        }

        TextButton(onClick = { onNavigate("recover_password") }) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}