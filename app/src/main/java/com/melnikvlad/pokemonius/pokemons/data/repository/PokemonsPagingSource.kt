package com.melnikvlad.pokemonius.pokemons.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.melnikvlad.pokemonius.pokemons.data.network.api.PokemonsApi
import com.melnikvlad.pokemonius.pokemons.data.network.models.Pokemon
import retrofit2.HttpException
import java.io.IOException

private const val PAGE_SIZE = 20

class PokemonsPagingSource(
    private val api: PokemonsApi
) : PagingSource<Int, Pokemon>() {

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val pageIndex = params.key ?: 0
        return try {
            val response = api.getPokemons(PAGE_SIZE, pageIndex * PAGE_SIZE)
            LoadResult.Page(
                data = response.pokemons,
                prevKey = if (pageIndex == 0) 0 else pageIndex - 1,
                nextKey = pageIndex + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}