package com.sihamark.pokemonlist.net

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.BufferedSource

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
class NetManager {

    private val client = OkHttpClient()

    fun loadGermanNames(): BufferedSource {
        val request = Request.Builder()
            .url(GERMAN_NAMES)
            .build()

        return client
            .newCall(request)
            .execute()
            .body()?.source() ?: error("could not read german pokemon names")
    }

    fun loadPokemonList(): List<Pokemon> {
        val request = Request.Builder()
            .url(POKEMON_LIST)
            .build()

        val source = client
            .newCall(request)
            .execute()
            .body()?.source() ?: error("could not read pokemon list")

        return parsePokemon(source)
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

    companion object {
        private const val POKEMON_LIST = "https://raw.githubusercontent.com/fanzeyi/pokemon.json/master/pokedex.json"
        private const val GERMAN_NAMES = "https://bulbapedia.bulbagarden.net/wiki/List_of_German_Pok%C3%A9mon_names"
    }
}