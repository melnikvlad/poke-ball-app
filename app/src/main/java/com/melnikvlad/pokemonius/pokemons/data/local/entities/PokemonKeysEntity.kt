package com.melnikvlad.pokemonius.pokemons.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val POKEMONS_KEYS_TABLE = "pokemonKeys"

@Entity(tableName = POKEMONS_KEYS_TABLE)
class PokemonKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val pokemonName: String,
    val prevPage: Int?,
    val nextPage: Int?
)