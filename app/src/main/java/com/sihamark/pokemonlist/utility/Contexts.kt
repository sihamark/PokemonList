package com.sihamark.pokemonlist.utility

import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */

val Context.inputManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager