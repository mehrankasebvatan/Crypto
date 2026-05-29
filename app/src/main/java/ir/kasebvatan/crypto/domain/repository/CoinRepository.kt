package ir.kasebvatan.crypto.domain.repository

import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoins(): Flow<List<Coin>>

    fun getCoinDetail(id: String): Flow<CoinDetail>
    
}