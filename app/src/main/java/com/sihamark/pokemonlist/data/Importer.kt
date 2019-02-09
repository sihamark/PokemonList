package com.sihamark.pokemonlist.data

import com.sihamark.pokemonlist.net.NetManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.BufferedSource
import timber.log.Timber


/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 07.02.2019.
 */
class Importer {

    private val netManager = NetManager()

    fun load() {
        val rawPokemons = netManager.loadRawPokemonList()
        val pokemons = rawPokemons.use {
            parsePokemon(rawPokemons)
        }

        Timber.e("parsed ${pokemons.size} pokemon and ${pokemons.flatMap { it.type }.distinct().size} types")
    }

    private fun parsePokemon(source: BufferedSource): List<Pokemon> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val type = Types.newParameterizedType(List::class.java, Pokemon::class.java)
        val adapter = moshi.adapter<List<Pokemon>>(type)

        return adapter.fromJson(source) ?: error("could not parse pokemon from source")
    }

    data class Pokemon(
        val id: Int,
        val name: Name,
        val type: List<String>
    ) {
        data class Name(
            val english: String,
            val japanese: String,
            val chinese: String
        )
    }
}