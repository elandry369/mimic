package com.cloakdroid.ui.profiles
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cloakdroid.data.local.ProfileEntity
@Composable fun ProfileListScreen(vm:ProfileViewModel,onLaunch:(ProfileEntity)->Unit,onEdit:(ProfileEntity)->Unit){ val profiles by vm.profiles.collectAsState(); val q by vm.query.collectAsState(); Scaffold(topBar={OutlinedTextField(q,{vm.query.value=it},Modifier.fillMaxWidth(),label={Text("Search profiles, tags, or proxy")})},floatingActionButton={FloatingActionButton({vm.quick()}){Text("+")}}){pad->LazyColumn(Modifier.padding(pad).padding(12.dp),verticalArrangement=Arrangement.spacedBy(8.dp)){items(profiles,key={it.id}){p->ProfileCard(p,{onLaunch(p)},{onEdit(p)},{vm.duplicate(p)},{vm.delete(p)})}}}}
@Composable private fun ProfileCard(p:ProfileEntity,launch:()->Unit,edit:()->Unit,duplicate:()->Unit,delete:()->Unit){ var menu by remember{mutableStateOf(false)}; Card(Modifier.fillMaxWidth()){Row(Modifier.padding(14.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){Column(Modifier.weight(1f)){Text(p.name,style=MaterialTheme.typography.titleMedium); Text("${p.tag} • ${p.proxy.type} • ${p.proxy.lastPingMs?.let{"${it}ms"}?:"untested"} • ${p.proxy.resolvedCountry?:"--"}",style=MaterialTheme.typography.bodySmall)}; IconButton(launch){Text("▶")}; Box{IconButton({menu=true}){Text("⋮")}; DropdownMenu(menu,{menu=false}){DropdownMenuItem({Text("Edit")},{edit()});DropdownMenuItem({Text("Duplicate")},{duplicate()});DropdownMenuItem({Text("Delete")},{delete()})}}}}}
