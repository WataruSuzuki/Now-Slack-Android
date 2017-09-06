package jp.co.devjchankchan.slackapilibrary

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import org.altbeacon.beacon.BeaconConsumer

class PhysicalBotService : Service(), BeaconConsumer {

    private val CHANNEL_ID = javaClass.toString()

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_REDELIVER_INTENT
    }

    override fun onCreate() {
        super.onCreate()

        notifyStartService()
    }

    override fun onBeaconServiceConnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun notifyStartService() {
        val manager = NotificationManagerCompat.from(applicationContext)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            manager.notify(NotificationId.START.ordinal, legacyNotificationBuilder(getString(NotificationId.START.resourceId)).build())
        } else {
            manager.notify(NotificationId.START.ordinal, notificationBuilder(getString(NotificationId.START.resourceId)).build())
        }
    }

    private fun legacyNotificationBuilder(title: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title)

        return builder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notificationBuilder(title: String): Notification.Builder {
        val builder = Notification.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title)
        return builder
    }

    enum class NotificationId(val resourceId: Int) {
        START(R.string.notify_start_service),
        STOP(R.string.notify_start_stop)
    }
}
