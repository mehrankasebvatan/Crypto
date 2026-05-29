package ir.kasebvatan.crypto.domain.usecase

import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<List<Coin>> {
        return repository.getCoins()
    }
}