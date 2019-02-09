package com.sihamark.pokemonlist.net

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

    fun loadRawPokemonList(): BufferedSource {
        val request = Request.Builder()
            .url(POKEMON_LIST)
            .build()

        return client
            .newCall(request)
            .execute()
            .body()?.source() ?: error("could not read pokemon list")
    }

    companion object {
        private const val POKEMON_LIST = "https://raw.githubusercontent.com/fanzeyi/pokemon.json/master/pokedex.json"
    }
}