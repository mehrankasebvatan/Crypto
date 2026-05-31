package ir.kasebvatan.crypto.domain.usecase

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCoinsUseCaseTest {

    private lateinit var repository: CoinRepository
    private lateinit var useCase: GetCoinsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCoinsUseCase(repository)
    }

    @Test
    fun `when repository returns coins, useCase emits Loading then Success`() = runTest {
        val fakeCoins = listOf(
            Coin(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                image = "",
                currentPrice = 50000.0,
                priceChangePercentage24h = 2.5,
                marketCap = 1000000L,
                marketCapRank = 1
            )
        )
        coEvery { repository.getCoins() } returns flowOf(fakeCoins)

        useCase().test {
            Assert.assertTrue(awaitItem() is Resource.Loading)
            val success = awaitItem()
            Assert.assertTrue(success is Resource.Success)
            Assert.assertEquals(fakeCoins, (success as Resource.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `when repository throws exception, useCase emits Loading then Error`() = runTest {
        coEvery { repository.getCoins() } throws Exception("Network error")

        useCase().test {
            Assert.assertTrue(awaitItem() is Resource.Loading)
            val error = awaitItem()
            Assert.assertTrue(error is Resource.Error)
            Assert.assertEquals("Network error", (error as Resource.Error).message)
            awaitComplete()
        }
    }
}