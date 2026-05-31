package ir.kasebvatan.crypto.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search coins...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true
        )

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
                        items(coins.size) { index ->
                            CoinListItem(
                                coin = coins[index],
                                onCoinClick = onCoinClick
                            )
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
}

@Composable
fun CoinListItem(
    coin: Coin,
    onCoinClick: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable {
                onCoinClick(coin.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = coin.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape), contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(text = "${coin.marketCapRank}. ${coin.name}", fontSize = 18.sp)
                Text(text = coin.symbol.uppercase(), fontSize = 12.sp)
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "$${coin.currentPrice}",
                )
                Text(
                    text = "${if (coin.priceChangePercentage24h >= 0) "+" else ""}${
                        "%.2f".format(
                            coin.priceChangePercentage24h
                        )
                    }%",
                    color = if (coin.priceChangePercentage24h >= 0)
                        Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontSize = 12.sp
                )
            }


        }
    }


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
    CryptoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {

            CoinListItem(
                Coin(
                    id = "bitcoin",
                    symbol = "btc",
                    name = "Bitcoin",
                    image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                    currentPrice = 50000.0,
                    priceChangePercentage24h = 2.5,
                    marketCap = 1000000L,
                    marketCapRank = 1
                )
            ) { }
        }
    }
}