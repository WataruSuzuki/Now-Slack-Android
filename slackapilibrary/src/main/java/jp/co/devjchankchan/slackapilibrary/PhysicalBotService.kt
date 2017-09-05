package jp.co.devjchankchan.slackapilibrary

import android.R
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.annotation.IntDef
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

class PhysicalBotService : Service() {

    private val CHANNEL_ID = javaClass.toString()

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //@IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true)
        return Service.START_REDELIVER_INTENT
    }

    override fun onCreate() {
        super.onCreate()

        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(1, legacyNotificationBuilder.build())
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

        }
    }

    private val legacyNotificationBuilder: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.drawable.sym_def_app_icon)
                .setContentTitle("(・∀・)")

}
