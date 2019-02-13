package com.sihamark.pokemonlist.data

import android.content.Context
import com.sihamark.pokemonlist.net.NetManager


/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 07.02.2019.
 */
class Importer(private val context: Context) {

    private val netManager = NetManager()

    fun load() {
        val pokemon = netManager.loadPokemonList()

        PokemonDao().use { dao ->
            dao.importTypes(pokemon.flatMap { it.type }.distinct())
            dao.importPokemon(pokemon)
        }

        val germanNameParser = GermanNameParser(context)
        val germanNames = germanNameParser.parse().map { it.number to it.name }

        PokemonDao().use { dao ->
            dao.importPokemonNames("de", germanNames)
        }
    }
}