package com.sihamark.pokemonlist.utility

import android.view.MenuItem

class FunctionalExpandListener(
    private val onExpand: (isExpanded: Boolean) -> Unit
) : MenuItem.OnActionExpandListener {
    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        onExpand(true)
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        onExpand(false)
        return true
    }
}
