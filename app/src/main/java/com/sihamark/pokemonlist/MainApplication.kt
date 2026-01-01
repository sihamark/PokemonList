package com.sihamark.pokemonlist

import android.app.Application
import androidx.core.app.NotificationCompat
import com.sihamark.pokemonlist.data.Importer
import com.sihamark.pokemonlist.data.PokemonDao
import com.sihamark.pokemonlist.ui.NotificationController
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 09.02.2019.
 */
@Suppress("unused")
class MainApplication : Application() {

    val inputBuilder: NotificationCompat.Builder by lazy {
        NotificationController.inputNotification(this)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .assetFile("pokemon.realm")
                .build()
        )

//        loadAndExportPokemon()
    }

    private fun loadAndExportPokemon() {
        GlobalScope.launch {
            try {
                Importer().load()
                PokemonDao().use {
                    it.copyToExternal(this@MainApplication)
                }
                Timber.e("successfully wrote realm")
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
    }
}