package com.melnikvlad.pokemonius.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melnikvlad.pokemonius.pokemons.presentation.viewmodel.PokemonsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Module
interface ViewModelModule {

    @Binds
    @Singleton
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PokemonsViewModel::class)
    fun pokemonsViewModel(viewModel: PokemonsViewModel): ViewModel
}