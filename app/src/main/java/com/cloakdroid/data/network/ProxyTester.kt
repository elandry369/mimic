package com.cloakdroid.data.network

import com.cloakdroid.data.local.ProxyConfig
import com.cloakdroid.data.local.ProxyType
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*

data class ProxyTestReport(val reachable: Boolean, val pingMs: Long?, val ip: String? = null, val countryCode: String? = null, val city: String? = null, val latitude: Double? = null, val longitude: Double? = null, val timezone: String? = null, val language: String? = null, val message: String? = null)
class ProxyTester {
 suspend fun testProxy(config: ProxyConfig): Result<ProxyTestReport> = runCatching {
  val proxy = when (config.type) { ProxyType.DIRECT -> Proxy.NO_PROXY; ProxyType.HTTP -> Proxy(Proxy.Type.HTTP, InetSocketAddress(config.host, config.port)); ProxyType.SOCKS5 -> Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved(config.host, config.port)) }
  val client = OkHttpClient.Builder().proxy(proxy).connectTimeout(12, TimeUnit.SECONDS).callTimeout(20, TimeUnit.SECONDS).apply {
   if (!config.username.isNullOrBlank()) proxyAuthenticator { _, response -> response.request.newBuilder().header("Proxy-Authorization", Credentials.basic(config.username, config.password.orEmpty())).build() }
  }.build()
  val start=System.nanoTime(); val body=client.newCall(Request.Builder().url("https://ipapi.co/json/").header("Accept", "application/json").build()).await().use { response -> if (!response.isSuccessful) error("HTTP ${response.code}"); response.body.string() }; val ping=TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start)
  fun field(name:String)=Regex("\\\"$name\\\"\\s*:\\s*\\\"([^\\\"]*)").find(body)?.groupValues?.get(1)
  fun number(name:String)=Regex("\\\"$name\\\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)").find(body)?.groupValues?.get(1)?.toDoubleOrNull()
  val country=field("country_code"); ProxyTestReport(true,ping,field("ip"),country,field("city"),number("latitude"),number("longitude"),field("timezone"),languageFor(country),null)
 }
 private fun languageFor(country:String?)=when(country?.uppercase(Locale.US)){"US","GB","CA","AU"->"en-US";"DE"->"de-DE";"FR"->"fr-FR";"ES"->"es-ES";"BR"->"pt-BR";"JP"->"ja-JP";else->"en-US"}
}
private suspend fun Call.await(): Response = suspendCancellableCoroutine { c -> enqueue(object: Callback { override fun onFailure(call: Call, e: java.io.IOException) { if(c.isActive)c.resumeWith(Result.failure(e)) }; override fun onResponse(call: Call, response: Response) { c.resume(response) } }); c.invokeOnCancellation { cancel() } }
