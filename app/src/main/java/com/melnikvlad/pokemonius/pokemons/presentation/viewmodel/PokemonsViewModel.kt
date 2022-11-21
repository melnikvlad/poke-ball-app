package com.melnikvlad.pokemonius.pokemons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel
import com.melnikvlad.pokemonius.pokemons.data.repository.PokemonsPagingSource
import com.melnikvlad.pokemonius.pokemons.domain.PokemonsRepository
import com.melnikvlad.pokemonius.util.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PokemonsViewModel @Inject constructor(
    private val pokemonsRepository: PokemonsRepository
) : ViewModel() {

    private val pageIndex: MutableStateFlow<Int> = MutableStateFlow(value = 1)
    val uiState = pageIndex.flatMapLatest { page ->
        pokemonsRepository.getPokemons(page)
            .map { State.Data(it)  }
            .catch { State.Error(it)  }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = State.Loading
            )
    }
}

sealed interface State {
    data class Data(val pokemons: PagingData<PokemonEntity>) : State
    data class Error(val exception: Throwable) : State
    object Loading : State
}