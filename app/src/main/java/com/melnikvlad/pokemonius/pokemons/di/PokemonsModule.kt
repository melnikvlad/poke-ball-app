package com.melnikvlad.pokemonius.pokemons.di

import com.melnikvlad.pokemonius.pokemons.data.repository.PokemonsRepositoryImpl
import com.melnikvlad.pokemonius.pokemons.domain.PokemonsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface PokemonsModule {

    @Binds
    @Singleton
    fun bindPokemonsRepository(repositoryImpl: PokemonsRepositoryImpl): PokemonsRepository

}