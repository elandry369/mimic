package com.cloakdroid.di

import android.content.Context
import androidx.room.Room
import com.cloakdroid.data.local.AppDatabase
import com.cloakdroid.data.network.ProxyTester
import com.cloakdroid.data.repository.ProfileRepository

class AppModule(context: Context) {
 private val database = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "cloakdroid.db").fallbackToDestructiveMigration().build()
 val repository = ProfileRepository(context.applicationContext, database.profileDao())
 val proxyTester = ProxyTester()
}
