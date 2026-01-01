package com.sihamark.pokemonlist.ui

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.sihamark.pokemonlist.R

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 07.02.2019.
 */

class MainActivity() : FragmentActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, MainFragment.newInstance())
            .commit()

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this, arrayOf(POST_NOTIFICATIONS), 0
            )
        }
        if (intent.action == "com.sihamark.pokemonlist.SHOW_NOTIFICATION") {
            NotificationController.showNotification(this)
        }
    }
}