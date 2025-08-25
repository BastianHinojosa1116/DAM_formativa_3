package com.example.formativa_1.screens

import android.app.Activity
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formativa_1.data.UserDataStore
import com.example.formativa_1.permissions.isMicrophonePermissionGranted
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun HomeScreen(userPrefs: UserDataStore, onNavigate: ((String) -> Unit)? = null) {

    val context = LocalContext.current
    val activity = context as? Activity
    var username by remember { mutableStateOf("Cargando...") }
    var micPermissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            micPermissionGranted = isGranted
            if (!isGranted) {
                Toast.makeText(context, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )


    LaunchedEffect(Unit) {
        username = userPrefs.getUsername.firstOrNull() ?: "Usuario"

        if (isMicrophonePermissionGranted(context)) {
            micPermissionGranted = true
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bienvenido: ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = username,
                fontSize = 24.sp
            )
        }



        if (micPermissionGranted) {
            Text("Permiso de micrófono concedido ✅")
        } else {
            Text("Esperando permiso de micrófono ❌")
        }
    }
}