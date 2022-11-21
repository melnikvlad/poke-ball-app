package com.melnikvlad.pokemonius.pokemons.data.network.models

import com.google.gson.annotations.SerializedName
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel

private const val IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
private const val IMAGE_EXT = ".png"

data class Pokemon(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    private val pokemonUrl: String
) {
    fun getPicUrl(): String {
        val index = pokemonUrl.split("/".toRegex()).dropLast(1).last()
        return "$IMAGE_URL$index$IMAGE_EXT"
    }
}

fun Pokemon.toEntity() = PokemonEntity(name = name, picUrl = getPicUrl())

fun List<Pokemon>.toEntity() = map { it.toEntity() }
