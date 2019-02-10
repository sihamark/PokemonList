package com.sihamark.pokemonlist.utility

import androidx.appcompat.widget.SearchView

class FunctionalSuggestionListener(
    private val onClick: (position: Int) -> Unit = {},
    private val onSelect: (position: Int) -> Unit = {}
) : SearchView.OnSuggestionListener {

    override fun onSuggestionSelect(position: Int): Boolean {
        onSelect(position)
        return true
    }

    override fun onSuggestionClick(position: Int): Boolean {
        onClick(position)
        return true
    }
}
