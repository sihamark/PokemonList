package com.sihamark.pokemonlist

import android.app.Application
import com.sihamark.pokemonlist.data.Importer
import io.realm.Realm
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

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Realm.init(this)

        GlobalScope.launch {
            try {
                Importer(this@MainApplication).load()
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
    }
}