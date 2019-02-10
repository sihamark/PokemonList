package com.sihamark.pokemonlist.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
open class SelectedPokemon(
    @PrimaryKey var number: Int = -1,
    var linkedPokemon: Pokemon? = null,
    var isSelected: Boolean = false
) : RealmObject() {

    val pokemon
        get() = linkedPokemon!!

    companion object {
        fun create(pokemon: Pokemon, isSelected: Boolean = false) = SelectedPokemon(pokemon.number, pokemon, isSelected)
    }
}