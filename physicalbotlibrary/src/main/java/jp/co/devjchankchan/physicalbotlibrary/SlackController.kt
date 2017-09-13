package jp.co.devjchankchan.physicalbotlibrary

import android.content.Context
import android.content.Intent

import allbegray.slack.SlackClientFactory
import allbegray.slack.webapi.SlackWebApiClient

class SlackController {

    private val slackToken: String
    private var mWebApiClient: SlackWebApiClient? = null

    val emojiList: Map<String, String>
        get() {
            mWebApiClient = SlackClientFactory.createWebApiClient(slackToken)
            val emojiList = mWebApiClient!!.emojiList
            println(emojiList.toString())

            return emojiList
        }

    init {
        slackToken = PhysicalBotService.KEY_ACCESS_TOKEN
    }

    companion object {

        fun requestAuthToken(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }
    }
}
