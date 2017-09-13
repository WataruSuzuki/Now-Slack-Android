package jp.co.devjchankchan.physicalbotlibrary

import android.content.Context
import android.content.Intent

import allbegray.slack.SlackClientFactory
import allbegray.slack.webapi.SlackWebApiClient
import android.os.Handler
import android.os.HandlerThread

class SlackController(context: Context) {

    private val slackToken: String
    private var mWebApiClient: SlackWebApiClient? = null

    private val emojiList: Map<String, String>
        get() {
            mWebApiClient = SlackClientFactory.createWebApiClient(slackToken)
            val emojiList = mWebApiClient!!.emojiList
            println(emojiList.toString())
            return emojiList
        }

    init {
        slackToken = PhysicalBotService.Companion.loadAccessToken(context)
        println("slackToken = "+ slackToken)
    }

    companion object {

        fun requestAuthToken(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }

        fun removeAuthToken(context: Context) {
            PhysicalBotService.saveAccessToken(context, "")
        }
    }

    fun loadEmojiList(onCompletion: (map: Map<String, String>) -> Unit) {
        val handlerThread = HandlerThread("emojiList")
        handlerThread.start()
        Handler(handlerThread.looper).post {
            onCompletion(emojiList)
        }
    }
}
