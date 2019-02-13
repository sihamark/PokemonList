package com.sihamark.pokemonlist.data

import okio.BufferedSource
import java.nio.charset.Charset

class GermanNameParser {

    fun parse(source: BufferedSource): List<PokemonName> {
        val rawPokemonNames = source.readString(Charset.defaultCharset())

        return regex.findAll(rawPokemonNames).mapNotNull {
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
