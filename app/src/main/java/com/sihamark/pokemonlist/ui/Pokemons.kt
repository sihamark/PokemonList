package com.sihamark.pokemonlist.ui

import android.content.Context
import com.sihamark.pokemonlist.model.Pokemon
import com.sihamark.pokemonlist.utility.language

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 15.02.2019.
 */

fun Pokemon.name(context: Context) = name(context.language)

fun Pokemon.formattedNumber() = String.format("#%03d", number)