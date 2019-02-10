package com.sihamark.pokemonlist.ui

import android.os.Bundle
import androidx.annotation.ContentView
import androidx.fragment.app.FragmentActivity
import com.sihamark.pokemonlist.R

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 07.02.2019.
 */
@ContentView(R.layout.activity_main)
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, MainFragment.newInstance())
            .commit()
    }
}