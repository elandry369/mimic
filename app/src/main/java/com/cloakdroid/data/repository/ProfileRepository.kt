package com.cloakdroid.data.repository

import android.content.Context
import com.cloakdroid.data.local.*
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.util.UUID

class ProfileRepository(private val context: Context, private val dao: ProfileDao) {
 val profiles: Flow<List<ProfileEntity>> = dao.observeAll()
 suspend fun profile(id: String) = dao.get(id)
 suspend fun save(profile: ProfileEntity) = dao.upsert(profile)
 suspend fun createQuickProfile(): ProfileEntity { val now=System.currentTimeMillis(); return ProfileEntity(UUID.randomUUID().toString(), "Profile ${now % 10000}", "Random", DEFAULT_UA, now, now, fingerprint=FingerprintConfig(noiseSeed=UUID.randomUUID().mostSignificantBits)).also { dao.upsert(it) } }
 suspend fun cloneProfile(source: ProfileEntity): ProfileEntity { val now=System.currentTimeMillis(); return source.copy(id=UUID.randomUUID().toString(), name="${source.name} copy", createdAt=now, lastUsedAt=now).also { dao.upsert(it) } }
 suspend fun delete(profile: ProfileEntity) { dao.delete(profile); File(context.filesDir, "profiles/${profile.id}").deleteRecursively() }
 suspend fun touch(id:String) = dao.touch(id, System.currentTimeMillis())
 companion object { const val DEFAULT_UA = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 Chrome/120.0 Mobile Safari/537.36" }
}
