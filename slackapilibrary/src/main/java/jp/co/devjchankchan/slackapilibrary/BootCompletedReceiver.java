package jp.co.devjchankchan.slackapilibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

    public static final String CUSTOM_INTENT = "jp.co.devjchankchan.slackapilibrary.TEST_START";

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("(・∀・) ヤッホー");
        Intent service = new Intent(context, PhysicalBotService.class);
        context.startService(service);
    }
}
