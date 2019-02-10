package com.sihamark.pokemonlist.utility

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.cursoradapter.widget.CursorAdapter

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
fun Activity.setupSearch(
    toolbar: Toolbar,
    @IdRes itemId: Int,
    @DrawableRes navigationIcon: Int,
    @StringRes searchHint: Int,
    onExpand: ((isExpanded: Boolean) -> Unit)? = null,
    onQuery: ((query: String) -> Unit)? = null,
    suggestionAdapter: CursorAdapter? = null,
    onSelectSuggestion: (position: Int) -> Unit = {}
) {
    SearchViewHandler(
        this,
        toolbar,
        itemId,
        navigationIcon,
        searchHint,
        onExpand,
        onQuery,
        suggestionAdapter,
        onSelectSuggestion
    )
}
