package com.sihamark.pokemonlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.sihamark.pokemonlist.data.PokemonDao
import com.sihamark.pokemonlist.data.PokemonDao.SearchResult.AddedToList
import com.sihamark.pokemonlist.data.PokemonDao.SearchResult.AlreadyInList
import com.sihamark.pokemonlist.data.PokemonDao.SearchResult.NotFound
import com.sihamark.pokemonlist.model.Pokemon
import com.sihamark.pokemonlist.ui.NotificationController
import com.sihamark.pokemonlist.ui.formattedNumber
import com.sihamark.pokemonlist.ui.name
import com.sihamark.pokemonlist.utility.language

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 15.02.2019.
 */
class AddPokemonReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pokemonQuery = RemoteInput.getResultsFromIntent(intent)!!
            .getCharSequence(NotificationController.KEY_INPUT_POKEMON)
        val message = handleSearchQuery(context, pokemonQuery?.toString())
        NotificationController.showNotification(context, message)
    }

    private fun handleSearchQuery(context: Context, query: String?): String {
        if (query == null || query.isBlank()) {
            return context.getString(R.string.input_result_enter_pokemon)
        }

        return PokemonDao().use { dao ->
            when (val searchResult = dao.searchAndSelect(query, context.language)) {
                NotFound -> context.getString(R.string.input_result_pokemon_not_found)
                is AlreadyInList ->
                    context.getString(
                        R.string.input_result_pokemon_already_in_list,
                        searchResult.pokemon.formattedPokemon(context)
                    )
                is AddedToList ->
                    context.getString(
                        R.string.input_result_pokemon_added_to_list,
                        searchResult.pokemon.formattedPokemon(context)
                    )
            }
        }
    }

    private fun Pokemon.formattedPokemon(context: Context) =
        "${formattedNumber()} \"${name(context)}\""

    companion object {
        const val REQUEST_ADD_POKEMON = 69
    }
}
