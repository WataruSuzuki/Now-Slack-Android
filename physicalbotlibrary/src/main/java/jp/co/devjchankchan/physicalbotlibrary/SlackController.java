package jp.co.devjchankchan.physicalbotlibrary;

import android.content.Context;
import android.content.Intent;

public class SlackController {
    public static void requestAuthToken(Context context) {
        context.startActivity(new Intent(context, AuthActivity.class));
    }
}
