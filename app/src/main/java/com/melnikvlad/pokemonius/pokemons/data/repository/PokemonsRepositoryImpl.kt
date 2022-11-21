package com.melnikvlad.pokemonius.pokemons.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.melnikvlad.pokemonius.pokemons.data.local.PokemonsDatabase
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.network.api.PokemonsApi
import com.melnikvlad.pokemonius.pokemons.domain.PokemonsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20

@OptIn(ExperimentalPagingApi::class)
class PokemonsRepositoryImpl @Inject constructor(
    private val api: PokemonsApi,
    private val pokemonsDatabase: PokemonsDatabase
) : PokemonsRepository {

    override suspend fun getPokemons(page: Int): Flow<PagingData<PokemonEntity>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 4,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = PokemonsRemoteMediator(api, pokemonsDatabase),
            pagingSourceFactory = { pokemonsDatabase.pokemonsDao().pagingSource() }
        ).flow
    }
}