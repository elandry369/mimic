package com.cloakdroid.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ProxyType { DIRECT, SOCKS5, HTTP }

data class ProxyConfig(val type: ProxyType = ProxyType.DIRECT, val host: String = "", val port: Int = 0, val username: String? = null, val password: String? = null, val resolvedCountry: String? = null, val resolvedIp: String? = null, val lastPingMs: Long? = null)
data class FingerprintConfig(val latitude: Double = 40.7128, val longitude: Double = -74.0060, val accuracy: Float = 25f, val timezone: String = "America/New_York", val locale: String = "en-US", val hardwareConcurrency: Int = 8, val deviceMemory: Int = 8, val noiseSeed: Long = 1L, val webrtcDisabled: Boolean = true, val canvasNoise: Boolean = true, val audioNoise: Boolean = true)
@Entity(tableName = "profiles")
data class ProfileEntity(@PrimaryKey val id: String, val name: String, val tag: String, val userAgent: String, val createdAt: Long, val lastUsedAt: Long, @Embedded(prefix = "proxy_") val proxy: ProxyConfig = ProxyConfig(), @Embedded(prefix = "fp_") val fingerprint: FingerprintConfig = FingerprintConfig())
