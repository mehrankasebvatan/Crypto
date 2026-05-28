package ir.kasebvatan.crypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: DescriptionDto,
    @SerializedName("image")
    val image: ImageDto,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("market_data")
    val marketData: MarketDataDto,
)

data class DescriptionDto(
    @SerializedName("en")
    val en: String,
)

data class ImageDto(
    @SerializedName("large")
    val large: String,
)

data class MarketDataDto(
    @SerializedName("current_price")
    val currentPrice: Map<String, Double>,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("market_cap")
    val marketCap: Map<String, Long>,
)