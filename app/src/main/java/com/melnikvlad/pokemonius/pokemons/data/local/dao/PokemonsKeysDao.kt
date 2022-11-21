package com.melnikvlad.pokemonius.pokemons.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.melnikvlad.pokemonius.pokemons.data.local.entities.POKEMONS_KEYS_TABLE
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonKeysEntity

@Dao
interface PokemonsKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<PokemonKeysEntity>)

    @Query("SELECT * FROM $POKEMONS_KEYS_TABLE WHERE pokemonName = :pokemonName")
    suspend fun getPokemonKeys(pokemonName: String): PokemonKeysEntity

    @Query("DELETE FROM $POKEMONS_KEYS_TABLE")
    suspend fun deleteAllKeys()
}