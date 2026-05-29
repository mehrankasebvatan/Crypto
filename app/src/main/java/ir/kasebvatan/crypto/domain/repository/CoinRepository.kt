package ir.kasebvatan.crypto.domain.repository

import androidx.paging.PagingData
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(): Flow<List<Coin>>
    fun getCoinDetail(id: String): Flow<CoinDetail>
    fun getCoinsPaged(): Flow<PagingData<Coin>>
}