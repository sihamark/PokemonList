package com.sihamark.pokemonlist.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sihamark.pokemonlist.R
import com.sihamark.pokemonlist.model.Pokemon
import com.sihamark.pokemonlist.realm.RealmRecyclerViewAdapter
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
@ContentView(R.layout.fragment_main)
class MainFragment : Fragment() {

    private val model: MainFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler.adapter = Adapter()
    }

    inner class Adapter : RealmRecyclerViewAdapter<Pokemon, Adapter.ViewHolder>(
        model.pokemon as OrderedRealmCollection<Pokemon>, true, true
    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position) ?: return)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(pokemon: Pokemon) {
                itemView.findViewById<TextView>(R.id.name)
                    .text = pokemon.name("en")
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}