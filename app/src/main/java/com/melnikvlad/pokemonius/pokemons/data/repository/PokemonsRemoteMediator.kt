package com.melnikvlad.pokemonius.pokemons.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.melnikvlad.pokemonius.pokemons.data.local.PokemonsDatabase
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonKeysEntity
import com.melnikvlad.pokemonius.pokemons.data.network.api.PokemonsApi
import com.melnikvlad.pokemonius.pokemons.data.network.models.Pokemon
import com.melnikvlad.pokemonius.pokemons.data.network.models.toEntity
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class PokemonsRemoteMediator(
    private val api: PokemonsApi,
    private val pokemonsDatabase: PokemonsDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> START_PAGE

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(remoteKeys != null)

                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(remoteKeys != null)

                    nextPage
                }
            }

            val response = api.getPokemons(
                state.config.pageSize,
                currentPage * state.config.pageSize
            )

            val endOfPaginationReached = response.isEmpty()
            val prevPage = if (currentPage == START_PAGE) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            pokemonsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonsDatabase.pokemonsDao().deleteAll()
                    pokemonsDatabase.pokemonsKeysDao().deleteAllKeys()
                }

                val pokemons = response.pokemons.toEntity()

                val keys = pokemons.map { pokemon ->
                    PokemonKeysEntity(
                        pokemonName = pokemon.name,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                pokemonsDatabase.pokemonsDao().insertAll(pokemons)
                pokemonsDatabase.pokemonsKeysDao().insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonEntity>
    ): PokemonKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let {
                pokemonsDatabase.pokemonsKeysDao().getPokemonKeys(it)
            }
        }
    }

    private suspend fun getRemoteKeyFirstItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonKeysEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let {
                pokemonsDatabase.pokemonsKeysDao().getPokemonKeys(it.name)
            }
    }

    private suspend fun getRemoteKeyLastItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let {
                pokemonsDatabase.pokemonsKeysDao().getPokemonKeys(it.name)
            }
    }
}