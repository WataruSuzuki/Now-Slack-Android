package jp.co.devjchankchan.slackapilibrary

import android.content.AsyncTaskLoader
import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.net.URL

class URLConnectionTaskLoader(context: Context, urlStr: String) : AsyncTaskLoader<String>(context) {

    private var reqUrlStr = urlStr

    override fun onStartLoading() {
        super.onStartLoading()
        println("(・∀・) request = "  + reqUrlStr)
        forceLoad()
    }

    override fun loadInBackground(): String {
        try {
            val url = URL(reqUrlStr)
            val connection = url.openConnection()
            connection.connect()

            val stream = connection.getInputStream()
            val allText = stream.bufferedReader().use(BufferedReader::readText)
            //println("(・∀・) allText = " + allText)

            return allText
        } catch (ioe: IOException) {
            return ""
        }
    }
}
