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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formativa_1.data.UserDataStore
import com.example.formativa_1.permissions.isMicrophonePermissionGranted
import kotlinx.coroutines.flow.firstOrNull


data class Receta(
    val nombre: String,
    val descripcion: String,
    val recomendacionNutricional: String
)

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


    val recetasSemanales = listOf(
        Receta("Ensalada de quinoa", "Quinoa con verduras frescas", "Alta en fibra y proteína vegetal"),
        Receta("Pollo al horno", "Pollo sin piel con papas", "Proteína magra y bajo en grasa"),
        Receta("Guiso de lentejas", "Lentejas con zanahoria y apio", "Rica en hierro"),
        Receta("Tortilla de espinaca", "Huevos con espinaca", "Fuente de vitamina A y proteínas"),
        Receta("Pescado al vapor", "Merluza con arroz integral", "Omega-3 y carbohidratos complejos")
    )

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



        Text(
            text = "Recetas semanales ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        recetasSemanales.forEach { receta ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = receta.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = receta.descripcion)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Recomendación: ${receta.recomendacionNutricional}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}