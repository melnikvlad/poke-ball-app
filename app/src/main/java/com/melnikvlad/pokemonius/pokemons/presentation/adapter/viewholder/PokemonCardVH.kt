package com.melnikvlad.pokemonius.pokemons.presentation.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.melnikvlad.pokemonius.R
import com.melnikvlad.pokemonius.pokemons.data.local.entities.PokemonEntity
import com.melnikvlad.pokemonius.pokemons.data.model.PokemonModel

class PokemonCardVH(view: View): RecyclerView.ViewHolder(view) {

    private var tvName: TextView = view.findViewById(R.id.tv_name)

    fun bind(item: PokemonEntity) {
        tvName.text = item.name
    }
}