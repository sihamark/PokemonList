package com.sihamark.pokemonlist.utility

import androidx.appcompat.widget.SearchView

class FunctionalQueryListener(
    private val onQueryChanged: (query: String) -> Unit
) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryChanged(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onQueryChanged(newText ?: "")
        return true
    }
}
