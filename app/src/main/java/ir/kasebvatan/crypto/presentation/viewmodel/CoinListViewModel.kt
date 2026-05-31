package ir.kasebvatan.crypto.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.kasebvatan.crypto.domain.model.Coin
import ir.kasebvatan.crypto.domain.model.Resource
import ir.kasebvatan.crypto.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _allCoins = MutableStateFlow<Resource<List<Coin>>>(Resource.Loading())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val state = combine(_allCoins, _searchQuery) { resource, query ->
        when (resource) {
            is Resource.Loading -> resource
            is Resource.Error -> resource
            is Resource.Success -> {
                val filtered = if (query.isEmpty()) {
                    resource.data ?: emptyList()
                } else {
                    resource.data?.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.symbol.contains(query, ignoreCase = true)
                    } ?: emptyList()
                }
                Resource.Success(filtered)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading()
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            _allCoins.value = result
        }.launchIn(viewModelScope)
    }
}