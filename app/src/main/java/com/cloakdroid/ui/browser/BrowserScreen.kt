package com.cloakdroid.ui.browser
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.cloakdroid.data.local.ProfileEntity
import com.cloakdroid.engine.GeckoSessionManager
import org.mozilla.geckoview.GeckoView
@Composable fun BrowserScreen(profile:ProfileEntity,manager:GeckoSessionManager,onClose:()->Unit){var url by remember{mutableStateOf("https://example.com")};var sheet by remember{mutableStateOf(false)};val session=remember(profile.id){manager.initProfile(profile)};Column{Row{IconButton(onClose){Text("×")};OutlinedTextField(url,{url=it},Modifier.weight(1f),singleLine=true);Button({manager.loadUrl(url)}){Text("Go")};AssistChip({sheet=true},{Text("Private")})};AndroidView({GeckoView(it).apply{setSession(session)}},Modifier.fillMaxSize())};if(sheet)ModalBottomSheet({sheet=false}){Column(Modifier.padding(androidx.compose.ui.unit.dp(24))){Text("Profile privacy",style=MaterialTheme.typography.titleLarge);Text("IP: ${profile.proxy.resolvedIp?:"not tested"}");Text("Location: ${profile.fingerprint.latitude}, ${profile.fingerprint.longitude}");Text("WebRTC: ${if(profile.fingerprint.webrtcDisabled)"blocked" else "enabled"}");Button({manager.clearSessionData();sheet=false}){Text("Wipe session data")}}}}
