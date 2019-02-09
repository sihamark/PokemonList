package com.sihamark.pokemonlist.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import java.util.*

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
open class Pokemon(
    var id: String = UUID.randomUUID().toString(),
    var type: RealmList<Type> = RealmList()
) : RealmObject() {

    @LinkingObjects("pokemon")
    val names: RealmResults<Name>? = null

    fun name(language: String): String =
        names?.find { it.language == language }?.name
            ?: error("no name for language $language found")
}