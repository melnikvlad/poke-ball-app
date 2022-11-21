package com.melnikvlad.pokemonius.pokemons.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.melnikvlad.pokemonius.pokemons.data.network.api.PokemonsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class NetworkModule {

    companion object {

        @Provides
        fun provideGson() = GsonBuilder().create()

        @Provides
        fun provideRetrofit(gson: Gson): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        @Provides
        fun providePokemonsApi(
            retrofit: Retrofit
        ): PokemonsApi = retrofit.create(PokemonsApi::class.java)
    }
}