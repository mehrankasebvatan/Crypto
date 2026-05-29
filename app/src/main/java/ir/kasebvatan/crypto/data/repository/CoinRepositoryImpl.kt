package ir.kasebvatan.crypto.data.repository

import ir.kasebvatan.crypto.data.remote.CoinGeckoApiService
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.CoinDetail
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApiService
) : CoinRepository {

    override fun getCoins(): Flow<List<Coin>> = flow {
        val coins = api.getCoins().map { it.toCoin() }
        emit(coins)
    }


    override fun getCoinDetail(id: String): Flow<CoinDetail> = flow {
        val coin = api.getCoinDetail(id).toCoinDetail()
        emit(coin)
    }


}