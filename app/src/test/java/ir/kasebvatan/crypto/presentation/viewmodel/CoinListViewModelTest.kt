package ir.kasebvatan.crypto.presentation.viewmodel

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.usecase.GetCoinsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinListViewModelTest {

    private lateinit var getCoinsUseCase: GetCoinsUseCase
    private lateinit var viewModel: CoinListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val fakeCoins = listOf(
        Coin(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "",
            currentPrice = 50000.0,
            priceChangePercentage24h = 2.5,
            marketCap = 1000000L,
            marketCapRank = 1
        ),
        Coin(
            id = "ethereum",
            symbol = "eth",
            name = "Ethereum",
            image = "",
            currentPrice = 3000.0,
            priceChangePercentage24h = 1.5,
            marketCap = 500000L,
            marketCapRank = 2
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCoinsUseCase = mockk()
        every { getCoinsUseCase() } returns flowOf(
            Resource.Loading(),
            Resource.Success(fakeCoins)
        )
        viewModel = CoinListViewModel(getCoinsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        viewModel.state.test {
            Assert.assertTrue(awaitItem() is Resource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when coins loaded, state becomes Success`() = runTest {
        viewModel.state.test {
            assertTrue(awaitItem() is Resource.Loading)
            testDispatcher.scheduler.advanceUntilIdle()
            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertEquals(fakeCoins, (success as Resource.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when search query changes, filtered coins returned`() = runTest {
        viewModel.state.test {
            awaitItem() // Loading
            testDispatcher.scheduler.advanceUntilIdle()
            awaitItem() // Success ba hame coin ha

            viewModel.onSearchQueryChanged("bitcoin")
            testDispatcher.scheduler.advanceUntilIdle()

            val filtered = awaitItem()
            assertTrue(filtered is Resource.Success)
            val coins = (filtered as Resource.Success).data ?: emptyList()
            assertEquals(1, coins.size)
            assertEquals("bitcoin", coins.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }
}