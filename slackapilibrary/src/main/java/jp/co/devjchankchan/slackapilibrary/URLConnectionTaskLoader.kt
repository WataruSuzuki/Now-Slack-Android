package jp.co.devjchankchan.slackapilibrary

import android.content.AsyncTaskLoader
import android.content.Context

class URLConnectionTaskLoader(context: Context) : AsyncTaskLoader<*>(context) {

    override fun loadInBackground(): Any? {
        return null
    }
}
