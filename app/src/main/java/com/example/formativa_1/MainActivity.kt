package com.example.formativa_1
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.formativa_1.navigation.AppNavGraph
import com.example.formativa_1.ui.theme.Formativa_1Theme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formativa_1Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavGraph(navController = navController)
                    }
                }



            }
        }
    }
}

