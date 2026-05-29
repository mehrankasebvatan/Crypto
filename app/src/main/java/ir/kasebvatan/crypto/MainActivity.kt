package ir.kasebvatan.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.kasebvatan.crypto.presentation.ui.NavGraph
import ir.kasebvatan.crypto.presentation.ui.theme.CryptoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

