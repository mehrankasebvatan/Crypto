package ir.kasebvatan.crypto.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.presentation.ui.theme.CryptoTheme
import ir.kasebvatan.crypto.presentation.viewmodel.CoinListViewModel

@Composable
fun CoinListScreen(
    onCoinClick: (String) -> Unit = {},
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val coins = viewModel.coinsPaged.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(
                count = coins.itemCount,
                key = coins.itemKey { it.id }
            ) { index ->
                val coin = coins[index]
                coin?.let {
                    CoinListItem(
                        coin =  coin,
                        onCoinClick = onCoinClick
                    )
                }

            }

            when (coins.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error loading more coins",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> {}
            }
        }

        when (coins.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is LoadState.Error -> {
                Text(
                    text = "Error loading coins",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {}
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