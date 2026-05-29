package ir.kasebvatan.crypto.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.kasebvatan.crypto.domain.model.CoinDetail
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.usecase.GetCoinDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<CoinDetail>>(Resource.Loading())
    val state: StateFlow<Resource<CoinDetail>> = _state

    init {
        savedStateHandle.get<String>("coinId")?.let { coinId ->
            getCoinDetail(coinId)
        }
    }

    private fun getCoinDetail(coinId: String) {
        getCoinDetailUseCase(coinId).onEach { result ->
            _state.value = result
        }.launchIn(viewModelScope)
    }
}