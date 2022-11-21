package com.melnikvlad.pokemonius.pokemons.domain

import androidx.paging.PagingData
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel
import com.melnikvlad.pokemonius.pokemons.data.network.models.Pokemon
import com.melnikvlad.pokemonius.util.network.Result
import kotlinx.coroutines.flow.Flow

interface PokemonsRepository {

    suspend fun getPokemons(page: Int): Flow<PagingData<PokemonEntity>>
}