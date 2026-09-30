package com.cloakdroid.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

class MainActivity : AppCompatActivity() {
    private lateinit var session: GeckoSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val geckoView = GeckoView(this)
        setContentView(geckoView)

        session = GeckoSession()
        session.open(GeckoRuntime.create(this))
        geckoView.setSession(session)
        session.loadUri("https://example.com")
    }

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }
}
