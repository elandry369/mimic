package com.cloakdroid.ui.profiles
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloakdroid.data.local.ProfileEntity
import com.cloakdroid.data.repository.ProfileRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class ProfileViewModel(private val repository: ProfileRepository):ViewModel(){ val query=MutableStateFlow(""); val profiles=combine(repository.profiles,query){p,q->p.filter{it.name.contains(q,true)||it.tag.contains(q,true)||it.proxy.type.name.contains(q,true)}}.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList()); fun quick(){viewModelScope.launch{repository.createQuickProfile()}}; fun delete(p:ProfileEntity){viewModelScope.launch{repository.delete(p)}}; fun duplicate(p:ProfileEntity){viewModelScope.launch{repository.cloneProfile(p)}}; fun save(p:ProfileEntity){viewModelScope.launch{repository.save(p)}} }
