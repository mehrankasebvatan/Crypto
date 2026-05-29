package ir.kasebvatan.crypto.domain.usecase

import ir.kasebvatan.crypto.domain.model.CoinDetail
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(id: String): Flow<CoinDetail> {
        return repository.getCoinDetail(id)
    }
}