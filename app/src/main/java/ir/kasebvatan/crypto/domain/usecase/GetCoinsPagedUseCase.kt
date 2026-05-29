package ir.kasebvatan.crypto.domain.usecase

import androidx.paging.PagingData
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsPagedUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<PagingData<Coin>> {
        return repository.getCoinsPaged()
    }
}