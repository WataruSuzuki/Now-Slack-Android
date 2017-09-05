package jp.co.devjchankchan.slackapilibrary;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class PhysicalBotService extends Service {

    private static final String CHANNEL_ID = PhysicalBotService.class.toString();
    public PhysicalBotService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //@IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true)
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(001, getLegacyNotificationBuilder().build());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

        }
    }

    private NotificationCompat.Builder getLegacyNotificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("(・∀・)");

        return builder;
    }

    enum NotificationId {

    }
}
