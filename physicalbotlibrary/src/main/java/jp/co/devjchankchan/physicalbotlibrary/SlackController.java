package jp.co.devjchankchan.physicalbotlibrary;

import android.content.Context;
import android.content.Intent;

import java.util.Map;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.webapi.SlackWebApiClient;

public class SlackController {

    private String slackToken;
    private SlackWebApiClient mWebApiClient;

    public SlackController() {
        slackToken = PhysicalBotService.Companion.getKEY_ACCESS_TOKEN();
    }

    public Map<String, String> getEmojiList() {
        mWebApiClient = SlackClientFactory.createWebApiClient(slackToken);
        Map<String, String> emojiList = mWebApiClient.getEmojiList();
        System.out.println(emojiList.toString());

        return emojiList;
    }

    public static void requestAuthToken(Context context) {
        context.startActivity(new Intent(context, AuthActivity.class));
    }
}
