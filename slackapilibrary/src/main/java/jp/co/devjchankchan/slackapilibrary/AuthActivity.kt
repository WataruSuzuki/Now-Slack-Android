package jp.co.devjchankchan.slackapilibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class AuthActivity : AppCompatActivity() {

    private val slackApiRequestAuth = "https://slack.com/oauth/authorize?&client_id="
    private val extraScope = "&scope=incoming-webhook,emoji:read,users.profile:read,users.profile:write"
    private val mySlackClientID = ""
    private val mySlackClientSecret = ""

    private lateinit var authWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setupWebView()
    }

    private fun setupWebView() {
        authWebView = findViewById(R.id.auth_web)
        authWebView.settings.javaScriptEnabled = true
        authWebView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                getAuthenticationCode(url)
                super.onPageFinished(view, url)
            }
        }
        authWebView.loadUrl(slackApiRequestAuth + mySlackClientID + extraScope)
    }

    private fun getAuthenticationCode(url: String?) {
        url?.let {
            println("(・∀・) indexOf = " + it.indexOf("?code=", 0, false))
            val startindex = it.indexOf("?code=", 0, false)
            if (startindex > 0) {
                var code = it.substring(startindex + "?code=".length)
                code = code.substring(0, code.indexOf("&", 0, false))
                println("(・∀・) code = " + code)
            }
        }
//            code = code.substring(to: stateRange.lowerBound)
//        }
//            if let result = WebAPI.oauthAccess(clientID: mySlackClientID, clientSecret: mySlackClientSecret, code: code, redirectURI: redirectUrlStr) {
//            if let accessToken = result["access_token"] as? String {
//                SlackKitHelpers.instance.accessToken = accessToken
//                self.dismiss(animated: true, completion: nil)
//            }
//        }
    }
}
