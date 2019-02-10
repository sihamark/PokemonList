package com.sihamark.pokemonlist.utility

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


object KeyboardUtility {
    /**
     * hide keyboard
     *
     * @param context Context
     * @param target  View that currently has focus
     */
    fun hideKeyboard(context: Context?, target: View?) {
        if (context == null || target == null) {
            return
        }

        val imm = getInputMethodManager(context)
        imm.hideSoftInputFromWindow(target.windowToken, 0)
    }

    /**
     * hide keyboard
     *
     * @param activity Activity
     */
    fun hideKeyboard(activity: Activity) {
        val view = activity.window.decorView

        hideKeyboard(activity, view)
    }

    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}
