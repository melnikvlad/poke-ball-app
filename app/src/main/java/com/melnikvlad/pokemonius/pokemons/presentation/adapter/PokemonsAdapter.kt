package com.melnikvlad.pokemonius.pokemons.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melnikvlad.pokemonius.R
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel
import com.melnikvlad.pokemonius.pokemons.presentation.adapter.viewholder.PokemonCardVH

class PokemonsAdapter(
    private val context: Context,
    private val onPokemonClick: ((PokemonEntity) -> Unit)? = null
) : PagingDataAdapter<PokemonEntity, RecyclerView.ViewHolder>(PokemonsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonCardVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is PokemonCardVH -> item?.let {
                holder.bind(it)
                holder.itemView.setOnClickListener { _ -> onPokemonClick?.invoke(it) }
            }
        }
    }
}