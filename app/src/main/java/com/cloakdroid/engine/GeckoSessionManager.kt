package com.cloakdroid.engine

import android.content.Context
import com.cloakdroid.data.local.ProfileEntity
import java.io.File
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings
import org.mozilla.geckoview.GeckoSession

/** One runtime/session at a time; each profile receives a private on-disk sandbox directory. */
class GeckoSessionManager private constructor(private val context: Context) : BrowserEngine {
 private var session: GeckoSession? = null
 private var runtime: GeckoRuntime? = null
 private var profile: ProfileEntity? = null
 override fun initProfile(profile: ProfileEntity): GeckoSession {
  destroy(); this.profile=profile
  File(context.filesDir, "profiles/${profile.id}").mkdirs()
  val settings=GeckoRuntimeSettings.Builder().javaScriptEnabled(true).build()
  runtime=GeckoRuntime.create(context, settings)
  return GeckoSession().also { it.open(runtime!!); session=it }
 }
 override fun loadUrl(url: String) { session?.loadUri(if (url.contains("://")) url else "https://$url") }
 override fun clearSessionData() { session?.loadUri("about:blank") }
 override fun destroy() { session?.close(); session=null; runtime?.shutdown(); runtime=null }
 companion object { @Volatile private var instance: GeckoSessionManager?=null; fun get(context: Context)=instance ?: synchronized(this) { instance ?: GeckoSessionManager(context.applicationContext).also { instance=it } } }
}
