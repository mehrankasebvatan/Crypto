package ir.kasebvatan.crypto.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.kasebvatan.crypto.data.local.dao.CoinDao
import ir.kasebvatan.crypto.data.remote.CoinGeckoApiService
import ir.kasebvatan.crypto.data.remote.CoinPagingSource
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.CoinDetail
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApiService,
    private val dao: CoinDao
) : CoinRepository {

    override fun getCoins(): Flow<List<Coin>> = flow {
        val cached = dao.getCoins().first()
        if (cached.isNotEmpty()) emit(cached.map { it.toCoin() })

        try {
            val remote = api.getCoins()
            dao.deleteCoins()
            dao.insertCoins(remote.map { it.toCoinEntity() })
        } catch (e: Exception) {
            if (cached.isEmpty()) throw e
        }

        emitAll(dao.getCoins().map { list -> list.map { it.toCoin() } })
    }

    override fun getCoinDetail(id: String): Flow<CoinDetail> = flow {
        val coin = api.getCoinDetail(id).toCoinDetail()
        emit(coin)
    }

    override fun getCoinsPaged(): Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { CoinPagingSource(api) }
        ).flow
    }
}