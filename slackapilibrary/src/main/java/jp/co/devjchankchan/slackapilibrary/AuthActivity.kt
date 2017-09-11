package jp.co.devjchankchan.slackapilibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.LoaderManager
import android.content.Loader
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import org.json.JSONObject

class AuthActivity : AppCompatActivity() {

    private val slackApiRequestAuth = "https://slack.com/oauth/authorize?&client_id="
    private val extraScope = "&scope=incoming-webhook,emoji:read,users.profile:read,users.profile:write"
    private val mySlackClientID = ""
    private val mySlackClientSecret = ""

    private val oauthAccess = "https://slack.com/api/oauth.access"
    private val redirectUrl = "https://watarusuzuki.github.io/Now-Slack-Android/"

    private val KEY_URL = "key_url"

    private lateinit var authWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setupWebView()
    }

    private val tokenLoaderCallbacks = object : LoaderManager.LoaderCallbacks<String> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> =  URLConnectionTaskLoader(applicationContext, args!!.getString(KEY_URL))

        override fun onLoadFinished(loader: Loader<String>?, data: String?) {
            //println("(・∀・) data = " + data)
            if (parseAccessTokenFromJson(JSONObject(data))) {
                finish()
            } else {
                Toast.makeText(this@AuthActivity, R.string.msg_fail_access_token, Toast.LENGTH_LONG).show()
            }
        }

        override fun onLoaderReset(loader: Loader<String>?) {
        }
    }

    private fun setupWebView() {
        authWebView = findViewById(R.id.auth_web)
        authWebView.settings.javaScriptEnabled = true
        authWebView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val code = getAuthenticationCode(url)
                if (code.isBlank()) {
                    super.onPageFinished(view, url)
                } else {
                    getOauthToken(code)
                }
            }
        }
        authWebView.loadUrl(slackApiRequestAuth + mySlackClientID + extraScope)
    }

    private fun getAuthenticationCode(url: String?) : String {
        url?.let {
            //println("(・∀・) indexOf = " + it.indexOf("?code=", 0, false))
            val startindex = it.indexOf("?code=", 0, false)
            if (startindex > 0) {
                var code = it.substring(startindex + "?code=".length)
                code = code.substring(0, code.indexOf("&", 0, false))
                println("(・∀・) code = " + code)

                return code
            }
        }

        return ""
    }

    private fun getOauthToken(code: String) {
        val urlStr = oauthAccess + "?&client_id=" + mySlackClientID + "&client_secret=" + mySlackClientSecret + "&code=" + code + "&redirect_uri=" + redirectUrl
        val bundle = Bundle()
        bundle.putString(KEY_URL, urlStr)

        loaderManager.initLoader(0, bundle, tokenLoaderCallbacks)
    }

    private fun parseAccessTokenFromJson(json: JSONObject) : Boolean {
        val accessToken = json.getString(PhysicalBotService.KEY_ACCESS_TOKEN)
        if (!accessToken.isNullOrBlank()) {
            println("(・∀・) accessToken = " + accessToken)
            PhysicalBotService.saveAccessToken(this, accessToken)

            return true
        }
        return false
    }
}
