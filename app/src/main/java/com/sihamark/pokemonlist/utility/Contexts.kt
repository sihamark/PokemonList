package com.sihamark.pokemonlist.utility

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.sihamark.pokemonlist.R

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */

val Context.inputManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

val Context.language: String
    get() = getString(R.string.language)

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, message, duration).apply {
        show()
    }