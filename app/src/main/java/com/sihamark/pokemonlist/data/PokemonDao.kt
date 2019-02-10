package com.sihamark.pokemonlist.data

import com.sihamark.pokemonlist.model.Name
import com.sihamark.pokemonlist.model.Pokemon
import com.sihamark.pokemonlist.model.SelectedPokemon
import com.sihamark.pokemonlist.model.Type
import com.sihamark.pokemonlist.net.NetManager
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import java.io.Closeable

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
class PokemonDao : Closeable {

    private val realm = Realm.getDefaultInstance()

    override fun close() {
        realm.close()
    }

    fun importTypes(types: List<String>) {
        realm.executeTransaction { realm ->
            types.forEach { type ->
                realm.insertOrUpdate(Type(type))
            }
        }
    }

    fun importPokemon(pokemon: List<NetManager.Pokemon>) {
        realm.executeTransaction { realm ->
            pokemon.forEach { pokemon ->
                realm.insertOrUpdate(
                    com.sihamark.pokemonlist.model.Pokemon(
                        pokemon.id,
                        RealmList(
                            Name(language = "en", name = pokemon.name.english),
                            Name(language = "ja", name = pokemon.name.japanese),
                            Name(language = "zh", name = pokemon.name.chinese)
                        ),
                        RealmList(
                            *pokemon.type.map { Type(it) }.toTypedArray()
                        )
                    )
                )
            }
        }
    }

    fun getPokemonSorted(): RealmResults<Pokemon> =
        realm.where<Pokemon>()
            .sort("number")
            .findAllAsync()

    fun getSelectedPokemonSorted(): RealmResults<SelectedPokemon> =
        realm.where<SelectedPokemon>()
            .equalTo("isSelected", true)
            .sort("linkedPokemon.number")
            .findAllAsync()

    fun setSelection(pokemon: Pokemon, select: Boolean) {
        realm.executeTransaction { realm ->
            val selectedPokemon = realm.where<SelectedPokemon>()
                .equalTo("number", pokemon.number)
                .findFirst()

            when {
                selectedPokemon != null -> selectedPokemon.isSelected = select
                select -> realm.insertOrUpdate(SelectedPokemon.create(pokemon, select))
            }
        }
    }
}