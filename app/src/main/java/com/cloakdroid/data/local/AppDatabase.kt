package com.cloakdroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class DbConverters { @TypeConverter fun fromProxyType(value: ProxyType) = value.name; @TypeConverter fun toProxyType(value: String) = ProxyType.valueOf(value) }
@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() { abstract fun profileDao(): ProfileDao }
