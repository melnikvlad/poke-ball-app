package com.melnikvlad.pokemonius.di

import android.content.Context
import com.melnikvlad.pokemonius.di.viewmodel.ViewModelModule
import com.melnikvlad.pokemonius.pokemons.di.DatabaseModule
import com.melnikvlad.pokemonius.pokemons.di.NetworkModule
import com.melnikvlad.pokemonius.pokemons.di.PokemonsModule
import com.melnikvlad.pokemonius.pokemons.presentation.PokemonsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        PokemonsModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: PokemonsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Context
        ): AppComponent
    }
}