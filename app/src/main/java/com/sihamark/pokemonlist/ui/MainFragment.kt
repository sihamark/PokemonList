package com.sihamark.pokemonlist.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.ContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sihamark.pokemonlist.MainApplication
import com.sihamark.pokemonlist.R
import com.sihamark.pokemonlist.utility.setupSearch
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
@ContentView(R.layout.fragment_main)
class MainFragment : Fragment() {

    private val allFragment by lazy { AllPokemonFragment.newInstance() }
    private val selectedFragment by lazy { SelectedPokemonFragment.newInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.inflateMenu(R.menu.search)
        toolbar.setOnMenuItemClickListener(::onClickMenuItem)
        navigation.setOnNavigationItemSelectedListener {
            val target = when (it.itemId) {
                R.id.action_navigation_all -> allFragment
                R.id.action_navigation_selected -> selectedFragment
                else -> null
            }

            if (target != null) {
                navigateTo(target)
            }

            true
        }

        navigateTo(allFragment)

        activity?.setupSearch(
            toolbar,
            R.id.action_search,
            0,
            R.string.search_title
        )
    }

    private fun navigateTo(target: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, target)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun onClickMenuItem(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_show_notification) {
            Notifications.showNotification(activity?.application as? MainApplication ?: return false)
            return true
        }
        return false
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}