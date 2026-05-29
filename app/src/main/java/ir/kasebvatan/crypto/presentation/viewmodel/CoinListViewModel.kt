package ir.kasebvatan.crypto.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.usecase.GetCoinsPagedUseCase
import ir.kasebvatan.crypto.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val getCoinsPagedUseCase: GetCoinsPagedUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<Resource<List<Coin>>>(Resource.Loading())
    val state: StateFlow<Resource<List<Coin>>> = _state


    val coinsPaged = getCoinsPagedUseCase()
        .cachedIn(viewModelScope)


    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            _state.value = result
        }.launchIn(viewModelScope)
    }
}