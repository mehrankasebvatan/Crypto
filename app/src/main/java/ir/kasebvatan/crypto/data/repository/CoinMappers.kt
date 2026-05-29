package ir.kasebvatan.crypto.data.repository

import ir.kasebvatan.crypto.data.local.entity.CoinEntity
import ir.kasebvatan.crypto.data.remote.dto.CoinDetailDto
import ir.kasebvatan.crypto.data.remote.dto.CoinDto
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.CoinDetail

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChangePercentage24h = priceChangePercentage24h,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
    )
}

fun CoinDetailDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        id = id,
        symbol = symbol,
        name = name,
        description = description.en,
        image = image.large,
        marketCapRank = marketCapRank,
        currentPrice = marketData.currentPrice["usd"] ?: 0.0,
        priceChangePercentage24h = marketData.priceChangePercentage24h,
        marketCap = marketData.marketCap["usd"] ?: 0L,
    )
}

fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChangePercentage24h = priceChangePercentage24h,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
    )
}

fun CoinDto.toCoinEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChangePercentage24h = priceChangePercentage24h,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
    )
}