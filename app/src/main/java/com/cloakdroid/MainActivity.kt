package com.cloakdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cloakdroid.data.local.ProfileEntity
import com.cloakdroid.engine.GeckoSessionManager
import com.cloakdroid.ui.browser.BrowserScreen
import com.cloakdroid.ui.profiles.*
import com.cloakdroid.ui.theme.CloakDroidTheme
class MainActivity:ComponentActivity(){override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);val app=application as CloakDroidApplication;setContent{CloakDroidTheme{val vm:ProfileViewModel=viewModel(factory=Factory(app.appModule.repository));var selected by remember{mutableStateOf<ProfileEntity?>(null)};var editing by remember{mutableStateOf<ProfileEntity?>(null)};when{selected!=null->BrowserScreen(selected!!,GeckoSessionManager.get(this@MainActivity)){selected=null};editing!=null->ProfileEditorScreen(editing!!,vm,app.appModule.proxyTester){editing=null};else->ProfileListScreen(vm,{selected=it},{editing=it})}}}}}
private class Factory(private val repo:com.cloakdroid.data.repository.ProfileRepository):ViewModelProvider.Factory{override fun <T:ViewModel> create(modelClass:Class<T>):T{@Suppress("UNCHECKED_CAST") return ProfileViewModel(repo) as T}}
