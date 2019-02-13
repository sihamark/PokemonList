package com.sihamark.pokemonlist.data

import android.content.Context
import com.sihamark.pokemonlist.R

class GermanNameParser(private val context: Context) {

    fun parse(): List<PokemonName> {
        val text = context.resources.openRawResource(R.raw.german_pokemon_names).use {
            it.bufferedReader().use {
                it.readText()
            }
        }

        return regex.findAll(text).mapNotNull {
            val parsedValues = it.groupValues
            PokemonName(
                parsedValues[1].toIntOrNull() ?: return@mapNotNull null,
                parsedValues[2]
            )
        }.toList()
    }

    data class PokemonName(
        val number: Int,
        val name: String
    )

    companion object {
        private val regex =
            Regex("""<tr style="background:#FFF">\n<td> (\w+)\n</td>\n.+\n.+\n.+\n.+\n.+">(\w+)</a>\n.+""")
    }
}
