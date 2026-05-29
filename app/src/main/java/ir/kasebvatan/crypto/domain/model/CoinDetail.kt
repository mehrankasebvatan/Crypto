package ir.kasebvatan.crypto.domain.model

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val description: String,
    val image: String,
    val marketCapRank: Int,
    val currentPrice: Double,
    val priceChangePercentage24h: Double,
    val marketCap: Long,
)