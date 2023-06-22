package com.skopisjiwa.presentation.user.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.skopisjiwa.data.datastore.DataStoreRepository
import com.skopisjiwa.utils.USER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore : DataStoreRepository
) : ViewModel() {

    val isLogin = dataStore.getIsLogin().asLiveData()

    private fun setIsLogin(isLogin : Boolean) {
        viewModelScope.launch {
            dataStore.setIslogin(isLogin)
        }
    }

    private fun setRoleId(roleId : Long) {
        viewModelScope.launch {
            dataStore.setRoleId(roleId)
        }
    }

    fun login(
        firestore: FirebaseFirestore,
        email : String,
        password : String,
        successToast : (Long) -> Unit,
        failureToast : () -> Unit,
    ) {
        firestore.collection(USER)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for (user in it.result){
                        if(user.getString("email") == email && user.getString("password") == password){
                            val role = user.getLong("roleId")?: 0L
                            Log.d("LoginViewModel", role.toString())
                            setRoleId(role)
                            successToast(role)
                            setIsLogin(true)
                        }
                    }
                }
            }
            .addOnFailureListener {
                failureToast()
            }
    }
}