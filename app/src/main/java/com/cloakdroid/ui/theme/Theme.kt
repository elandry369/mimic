package com.cloakdroid.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
private val colors=darkColorScheme(primary=Indigo, background=Background, surface=CardSurface, surfaceVariant=ElevatedCard, onBackground=TextPrimary, onSurface=TextPrimary, onSurfaceVariant=TextSecondary)
@Composable fun CloakDroidTheme(content:@Composable ()->Unit)=MaterialTheme(colorScheme=colors, content=content)
