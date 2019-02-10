package com.sihamark.pokemonlist.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
open class Type(
    @PrimaryKey var name: String = ""
) : RealmObject()