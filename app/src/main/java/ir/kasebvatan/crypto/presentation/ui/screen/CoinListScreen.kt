package ir.kasebvatan.crypto.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.presentation.ui.theme.CryptoTheme
import ir.kasebvatan.crypto.presentation.viewmodel.CoinListViewModel


@Composable
fun CoinListScreen(
    onCoinClick: (String) -> Unit = {},
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = state) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is Resource.Success -> {
                val coins = currentState.data ?: emptyList()
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(coins) { coin ->
                        CoinListItem(
                            coin = coin
                        ) { id ->
                            onCoinClick(id)
                        }
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = currentState.message ?: "Unknown error",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

}

@Composable
fun CoinListItem(
    coin: Coin,
    onCoinClick: (String) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable {
            onCoinClick(coin.id)
        },
        headlineContent = {
            Text(text = "${coin.marketCapRank}. ${coin.name}")
        },
        supportingContent = {
            Text(text = coin.symbol.uppercase())
        },
        trailingContent = {
            Text(
                text = "$${coin.currentPrice}",
                color = if (coin.priceChangePercentage24h >= 0)
                    Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun CoinListScreenPreview() {
    CryptoTheme() {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            CoinListScreen()
        }
    }
}