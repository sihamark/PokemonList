package com.sihamark.pokemonlist.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sihamark.pokemonlist.R
import com.sihamark.pokemonlist.databinding.FragmentPokemonListBinding
import com.sihamark.pokemonlist.model.SelectedPokemon
import com.sihamark.pokemonlist.realm.RealmRecyclerViewAdapter
import io.realm.OrderedRealmCollection

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
class SelectedPokemonFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val model: MainFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPokemonListBinding.bind(view)
        binding.adapter = Adapter()
    }

    inner class Adapter : RealmRecyclerViewAdapter<SelectedPokemon, PokemonViewHolder>(
        model.selectedPokemon as OrderedRealmCollection<SelectedPokemon>, true, true
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PokemonViewHolder.create(layoutInflater, parent)

        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            val pokemon = getItem(position)?.pokemon
            holder.bind(pokemon ?: return) { model.deselect(pokemon) }
        }

    }

    companion object {
        fun newInstance() = SelectedPokemonFragment()
    }

}