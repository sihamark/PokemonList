package com.sihamark.pokemonlist.utility

import android.app.Activity
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.cursoradapter.widget.CursorAdapter

class SearchViewHandler(
    private val activity: Activity,
    private val toolbar: Toolbar,
    @IdRes
    private val itemId: Int,
    @DrawableRes
    private val navigationIcon: Int,
    @StringRes
    private val searchHint: Int,
    private val onExpand: ((isExpanded: Boolean) -> Unit)?,
    onQuery: ((query: String) -> Unit)? = null,
    suggestionAdapter: CursorAdapter? = null,
    onSelectSuggestion: (position: Int) -> Unit = {}
) {

    private val searchView: SearchView

    init {
        val searchItem = toolbar.menu.findItem(itemId)
        searchItem.setOnActionExpandListener(FunctionalExpandListener(this::onRawExpand))

        searchView = searchItem.actionView as SearchView
        searchView.queryHint = activity.getString(searchHint)
        searchView.setIconifiedByDefault(false)

        if (onQuery != null) {
            searchView.setOnQueryTextListener(FunctionalQueryListener { onQuery(it) })
        }

        suggestionAdapter?.let {
            searchView.suggestionsAdapter = it
            searchView.setOnSuggestionListener(
                FunctionalSuggestionListener(onClick = onSelectSuggestion)
            )
        }

        searchView.isFocusableInTouchMode = true

        searchView.setOnQueryTextFocusChangeListener(this::onFocusChange)
    }

    private fun onRawExpand(isExpanded: Boolean) {
        if (isExpanded) {
            toolbar.navigationIcon = null
            searchView.requestFocusFromTouch()
        } else {
            if (navigationIcon != 0) {
                toolbar.setNavigationIcon(navigationIcon)
            }
            KeyboardUtility.hideKeyboard(activity)
        }
        onExpand?.invoke(isExpanded)
    }

    private fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            view.findFocus().let {
                it.post { activity.inputManager?.showSoftInput(it, 0) }
            }
        }
    }
}