package com.sihamark.pokemonlist.ui

import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.sihamark.pokemonlist.AddPokemonReceiver
import com.sihamark.pokemonlist.R
import com.sihamark.pokemonlist.data.PokemonDao
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
            .setContentTitle(context.getString(R.string.input_notification_title))
            .setContentText(context.getString(R.string.input_notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false)
            .setContentIntent(getContentPendingIntent(context))
            .addAction(getRemoteAddAction(context))

    fun showNotification(context: Context, message: String? = null) {
        val builder = context.mainApplication.inputBuilder

        builder.setStyle(
            NotificationCompat.BigTextStyle().bigText(
                buildSpannedString {
                    bold { append(context.getString(R.string.input_notification_text)) }
                    val pokemon = PokemonDao.namesOfSelectedPokemon(context)
                    if (pokemon.isNotBlank()) {
                        append("\n")
                        append(pokemon)
                    }
                })
        )

        if (message != null) {
            builder.setRemoteInputHistory(arrayOf(message))
        }

        val manager = NotificationManagerCompat.from(context)

        createChannel(context, manager)

        manager.notify(NOTIFICATION_INPUT, builder.build())
    }

    private fun getRemoteAddAction(context: Context): NotificationCompat.Action {
        val label = context.getString(R.string.input_notification_label_add_pokemon)
        val title = context.getString(R.string.input_notification_title_add_pokemon)

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_INPUT_POKEMON).run {
            setLabel(label)
            build()
        }
        val replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            AddPokemonReceiver.REQUEST_ADD_POKEMON,
            Intent(context, AddPokemonReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action.Builder(R.drawable.ic_add, title, replyPendingIntent)
            .addRemoteInput(remoteInput)
            .build()
    }

    private fun getContentPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    private fun createChannel(context: Context, manager: NotificationManagerCompat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val description = context.getString(R.string.notification_channel_description)

            val channel = NotificationChannel(CHANNEL_INPUT, name, IMPORTANCE_DEFAULT)
            channel.description = description
            manager.createNotificationChannel(channel)
        }
    }
}