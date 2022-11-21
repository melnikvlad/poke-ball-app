package com.melnikvlad.pokemonius.pokemons.data.network.api

import com.melnikvlad.pokemonius.pokemons.data.network.models.PokemonsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonsNetworkDataSource @Inject constructor(
    private val api: PokemonsApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    companion object {
        private const val PAGE_SIZE = 20
        private const val OFFSET = 0
    }

    suspend fun getPokemons(page: Int): PokemonsResponse = withContext(ioDispatcher) {
        api.getPokemons(limit = PAGE_SIZE, offset = page * OFFSET)
    }
}