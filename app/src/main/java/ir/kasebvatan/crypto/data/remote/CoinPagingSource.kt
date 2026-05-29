package ir.kasebvatan.crypto.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.kasebvatan.crypto.data.repository.toCoin
import ir.kasebvatan.crypto.domain.model.Coin
import javax.inject.Inject

class CoinPagingSource @Inject constructor(
    private val api : CoinGeckoApiService
) : PagingSource<Int, Coin>() {
    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val page = params.key ?: 1
        return try {
            val coins = api.getCoins(
                page = page,
                perPage = params.loadSize
            ).map { it.toCoin() }

            LoadResult.Page(
                data = coins,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (coins.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}