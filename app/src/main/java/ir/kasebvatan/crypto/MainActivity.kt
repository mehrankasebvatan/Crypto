package ir.kasebvatan.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ir.kasebvatan.crypto.presentation.ui.screen.CoinListScreen
import ir.kasebvatan.crypto.presentation.ui.theme.CryptoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTheme {
                CoinListScreen()
            }
        }
    }
}

