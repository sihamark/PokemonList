package com.sihamark.pokemonlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihamark.pokemonlist.databinding.ItemPokemonBinding
import com.sihamark.pokemonlist.model.Pokemon

class PokemonViewHolder(
    private val binding: ItemPokemonBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: Pokemon, onClick: () -> Unit) {
        binding.number = String.format("#%03d", pokemon.number)
        binding.name = pokemon.name("de")
        binding.setOnClick(onClick)
    }

    companion object {
        fun create(inflater: LayoutInflater, root: ViewGroup) =
            PokemonViewHolder(ItemPokemonBinding.inflate(inflater, root, false))
    }
}