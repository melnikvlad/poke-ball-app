package com.melnikvlad.pokemonius.pokemons.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.melnikvlad.pokemonius.pokemons.data.local.dao.PokemonsDao
import com.melnikvlad.pokemonius.pokemons.data.local.dao.PokemonsKeysDao
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonKeysEntity

@Database(
    entities = [PokemonEntity::class, PokemonKeysEntity::class],
    version = 1,
    exportSchema = true
)
abstract class PokemonsDatabase : RoomDatabase() {

    abstract fun pokemonsDao(): PokemonsDao
    abstract fun pokemonsKeysDao(): PokemonsKeysDao
}