package com.melnikvlad.pokemonius.pokemons.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melnikvlad.pokemonius.PokeApp
import com.melnikvlad.pokemonius.R
import com.melnikvlad.pokemonius.di.viewmodel.ViewModelFactory
import com.melnikvlad.pokemonius.pokemons.presentation.adapter.PokemonsAdapter
import com.melnikvlad.pokemonius.pokemons.presentation.viewmodel.PokemonsViewModel
import com.melnikvlad.pokemonius.pokemons.presentation.viewmodel.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonsFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val pokemonsAdapter: PokemonsAdapter by lazy { PokemonsAdapter(requireContext()) }

    private val viewModel: PokemonsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PokemonsViewModel::class.java]
    }

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as PokeApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_pokemons, container, false)

        recyclerView = view.findViewById(R.id.rv_pokemons)

        return view
    }

    fun initRecyclerView(adapter: PokemonsAdapter) {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(pokemonsAdapter)

        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is State.Data -> {
                            pokemonsAdapter.submitData(state.pokemons)
                        }
                        is State.Error -> Unit
                        State.Loading -> Unit
                    }
                }
            }
        }
    }
}