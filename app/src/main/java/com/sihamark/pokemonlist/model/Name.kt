package com.sihamark.pokemonlist.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
open class Name(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var language: String = "",
    var name: String = ""
) : RealmObject()