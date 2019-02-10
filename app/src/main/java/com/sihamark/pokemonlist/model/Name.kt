package com.sihamark.pokemonlist.model

import io.realm.RealmObject

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
open class Name(
    var language: String = "",
    var name: String = ""
) : RealmObject()