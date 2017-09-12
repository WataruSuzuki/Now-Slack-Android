package jp.co.devjchankchan.physicalbotlibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        println("(・∀・) ヤッホー")
        val service = Intent(context, PhysicalBotService::class.java)
        context.startService(service)
    }
}
