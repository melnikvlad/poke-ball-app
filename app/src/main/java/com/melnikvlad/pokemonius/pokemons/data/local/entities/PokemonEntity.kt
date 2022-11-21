package com.melnikvlad.pokemonius.pokemons.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel

const val POKEMONS_TABLE = "pokemons"

@Entity(tableName = POKEMONS_TABLE)
class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val picUrl: String
)