package com.cloakdroid.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao interface ProfileDao {
 @Query("SELECT * FROM profiles ORDER BY lastUsedAt DESC") fun observeAll(): Flow<List<ProfileEntity>>
 @Query("SELECT * FROM profiles WHERE id = :id") suspend fun get(id: String): ProfileEntity?
 @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(profile: ProfileEntity)
 @Delete suspend fun delete(profile: ProfileEntity)
 @Query("UPDATE profiles SET lastUsedAt = :time WHERE id = :id") suspend fun touch(id: String, time: Long)
}
