package com.sihamark.pokemonlist.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
open class Pokemon(
    @PrimaryKey var number: Int = -1,
    var names: RealmList<Name> = RealmList(),
    var type: RealmList<Type> = RealmList()
) : RealmObject() {

    fun name(language: String): String =
        names.find { it.language == language }?.name
            ?: names.find { it.language == "en" }?.name
            ?: error("no name for language $language found")
}