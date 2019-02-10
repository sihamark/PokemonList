package com.sihamark.pokemonlist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sihamark.pokemonlist.model.Pokemon
import io.realm.Realm
import io.realm.kotlin.where

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 10.02.2019.
 */
class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val realm = Realm.getDefaultInstance()

    val pokemon
        get() = realm.where<Pokemon>()
            .sort("number")
            .findAllAsync()

    override fun onCleared() {
        realm.close()
    }


}