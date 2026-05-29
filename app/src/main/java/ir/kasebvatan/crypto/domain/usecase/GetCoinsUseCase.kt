package ir.kasebvatan.crypto.domain.usecase

import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())
        try {
            repository.getCoins().collect { coins ->
                emit(Resource.Success(coins))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}