package com.sihamark.pokemonlist.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.sihamark.pokemonlist.AddPokemonReceiver
import com.sihamark.pokemonlist.R
import com.sihamark.pokemonlist.utility.mainApplication

/**
 * @author Hans Markwart (fanaloce@gmail.com)
 *
 * created at 15.02.2019.
 */
object Notifications {

    private const val NOTIFICATION_INPUT = 1
    private const val CHANNEL_INPUT = "input"

    const val KEY_INPUT_POKEMON = "input_pokemon"

    fun inputNotification(context: Context): NotificationCompat.Builder =
        NotificationCompat.Builder(context, Notifications.CHANNEL_INPUT)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Pokemon List")
            .setContentText("Add Pokemon by Name or by Number")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false)
            .setContentIntent(getContentPendingIntent(context))
            .addAction(getRemoteAddAction(context))

    fun showNotification(context: Context) {
        val builder = context.mainApplication.inputBuilder

        val manager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_INPUT, "Input", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Add Pokemon to the list via the notification"
            manager.createNotificationChannel(channel)
        }

        manager.notify(NOTIFICATION_INPUT, builder.build())
    }

    private fun getRemoteAddAction(context: Context): NotificationCompat.Action {
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_INPUT_POKEMON).run {
            setLabel("Add Pokemon")
            build()
        }
        val replyPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                AddPokemonReceiver.REQUEST_ADD_POKEMON,
                Intent(context, AddPokemonReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        return NotificationCompat.Action.Builder(R.drawable.ic_add, "Add Pokemon", replyPendingIntent)
            .addRemoteInput(remoteInput)
            .build()
    }

    private fun getContentPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, 0)
    }
}