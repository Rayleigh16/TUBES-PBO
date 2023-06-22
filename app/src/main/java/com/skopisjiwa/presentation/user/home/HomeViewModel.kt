package com.skopisjiwa.presentation.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skopisjiwa.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore : DataStoreRepository
) : ViewModel() {

    val roleId = dataStore.getRoleId().asLiveData()

    fun setIsLogin(isLogin : Boolean) {
        viewModelScope.launch {
            dataStore.setIslogin(isLogin)
        }
    }

    fun setRoleId(roleId : Long) {
        viewModelScope.launch {
            dataStore.setRoleId(roleId)
        }
    }

}