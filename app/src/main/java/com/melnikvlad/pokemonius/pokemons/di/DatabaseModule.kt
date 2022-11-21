package com.melnikvlad.pokemonius.pokemons.di

import android.content.Context
import androidx.room.Room
import com.melnikvlad.pokemonius.pokemons.data.local.PokemonsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): PokemonsDatabase {
        return Room
            .databaseBuilder(context, PokemonsDatabase::class.java, "pokemons_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}