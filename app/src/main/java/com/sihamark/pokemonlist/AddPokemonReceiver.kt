package com.sihamark.pokemonlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.sihamark.pokemonlist.data.PokemonDao
import com.sihamark.pokemonlist.data.PokemonDao.SearchResult.*
import com.sihamark.pokemonlist.model.Pokemon
import com.sihamark.pokemonlist.ui.Notifications
import com.sihamark.pokemonlist.ui.formattedNumber
import com.sihamark.pokemonlist.ui.name
import com.sihamark.pokemonlist.utility.language
import com.sihamark.pokemonlist.utility.toast

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 15.02.2019.
 */
class AddPokemonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pokemonQuery = RemoteInput.getResultsFromIntent(intent).getCharSequence(Notifications.KEY_INPUT_POKEMON)
        if (pokemonQuery == null || pokemonQuery.isBlank()) {
            context.toast("Please enter a Pokemon name or number!")
        } else {
            PokemonDao().use { dao ->
                val searchResult = dao.searchAndSelect(pokemonQuery.toString(), context.language)
                when (searchResult) {
                    NotFound -> context.toast("No Pokemon Found!")
                    is AlreadyInList -> context.toast("${searchResult.pokemon.formattedPokemon(context)} is already in the list.")
                    is AddedToList -> context.toast("${searchResult.pokemon.formattedPokemon(context)} was added to the list.")
                }
            }
        }
        Notifications.showNotification(context)
    }

    private fun Pokemon.formattedPokemon(context: Context) =
        "${formattedNumber()} \"${name(context)}\""

    companion object {
        const val REQUEST_ADD_POKEMON = 69
    }
}
