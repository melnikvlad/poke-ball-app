package com.melnikvlad.pokemonius.pokemons.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.melnikvlad.pokemonius.pokemons.data.local.entities.POKEMONS_TABLE
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity

@Dao
interface PokemonsDao {

    @Query("SELECT * FROM $POKEMONS_TABLE")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM $POKEMONS_TABLE")
    suspend fun deleteAll()
}