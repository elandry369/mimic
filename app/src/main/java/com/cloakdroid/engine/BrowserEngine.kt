package com.cloakdroid.engine

import com.cloakdroid.data.local.ProfileEntity
import org.mozilla.geckoview.GeckoSession

interface BrowserEngine {
 fun initProfile(profile: ProfileEntity): GeckoSession
 fun loadUrl(url: String)
 fun clearSessionData()
 fun destroy()
}
