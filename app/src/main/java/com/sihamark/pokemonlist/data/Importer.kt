package com.sihamark.pokemonlist.data

import com.sihamark.pokemonlist.net.NetManager


/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 07.02.2019.
 */
class Importer {

    private val netManager = NetManager()

    fun load() {
        val pokemon = netManager.loadPokemonList()

        PokemonDao().use { dao ->
            val importer = dao.Importer()
            importer.importTypes(pokemon.flatMap { it.type }.distinct())
            importer.importPokemon(pokemon)
        }

        val germanNameSource = netManager.loadGermanNames()
        val germanNames = GermanNameParser()
            .parse(germanNameSource)
            .map { it.number to it.name }

        PokemonDao().use { dao ->
            dao.Importer().importPokemonNames("de", germanNames)
        }
    }
}