package ir.kasebvatan.crypto.domain.usecase

import ir.kasebvatan.crypto.domain.model.CoinDetail
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(id: String): Flow<Resource<CoinDetail>> = flow {
        emit(Resource.Loading())
        try {
            repository.getCoinDetail(id).collect { coin ->
                emit(Resource.Success(coin))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}