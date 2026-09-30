package com.cloakdroid

import android.app.Application
import com.cloakdroid.di.AppModule
class CloakDroidApplication : Application() { val appModule by lazy { AppModule(this) } }
