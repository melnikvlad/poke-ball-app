package com.melnikvlad.pokemonius.pokemons.data.network.api

import com.melnikvlad.pokemonius.pokemons.data.network.models.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonsApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonsResponse
}