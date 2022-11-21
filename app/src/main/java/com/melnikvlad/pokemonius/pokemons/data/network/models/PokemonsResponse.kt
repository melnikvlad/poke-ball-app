package com.melnikvlad.pokemonius.pokemons.data.network.models

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val nextPage: String?,
    @SerializedName("previous")
    val previousPage: String?,
    @SerializedName("results")
    val pokemons: List<Pokemon>
) {
    fun isEmpty() = pokemons.isEmpty()
}
