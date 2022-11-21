package com.melnikvlad.pokemonius.pokemons.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel

class PokemonsDiffCallback: DiffUtil.ItemCallback<PokemonEntity>() {

    override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
        return oldItem == newItem
    }
}