package ir.kasebvatan.crypto.data.remote

import ir.kasebvatan.crypto.data.remote.dto.CoinDetailDto
import ir.kasebvatan.crypto.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApiService {

    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): List<CoinDto>


    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id: String
    ): CoinDetailDto


}