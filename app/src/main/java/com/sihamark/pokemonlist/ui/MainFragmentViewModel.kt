package com.sihamark.pokemonlist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sihamark.pokemonlist.data.PokemonDao
import com.sihamark.pokemonlist.model.Pokemon

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = PokemonDao()

    val pokemon
        get() = dao.getPokemonSorted()

    val selectedPokemon
        get() = dao.getSelectedPokemonSorted()


    override fun onCleared() {
        dao.close()
    }

    fun select(pokemon: Pokemon) {
        dao.setSelection(pokemon, true)
    }

    fun deselect(pokemon: Pokemon) {
        dao.setSelection(pokemon, false)
    }


}