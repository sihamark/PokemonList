package com.sihamark.pokemonlist.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sihamark.pokemonlist.MainApplication
import com.sihamark.pokemonlist.R
import com.sihamark.pokemonlist.databinding.FragmentMainBinding
import com.sihamark.pokemonlist.utility.setupSearch

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private val allFragment by lazy { AllPokemonFragment.newInstance() }
    private val selectedFragment by lazy { SelectedPokemonFragment.newInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainBinding.bind(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
        binding.toolbar.inflateMenu(R.menu.search)
        binding.toolbar.setOnMenuItemClickListener(::onClickMenuItem)
        binding.navigation.setOnItemSelectedListener {
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
            binding.toolbar,
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
            NotificationController.showNotification(
                activity?.application as? MainApplication ?: return false
            )
            return true
        }
        return false
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}