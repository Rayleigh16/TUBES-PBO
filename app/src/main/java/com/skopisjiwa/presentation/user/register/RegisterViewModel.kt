package com.skopisjiwa.presentation.user.register

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.skopisjiwa.data.user.UserModel
import com.skopisjiwa.utils.Resource
import com.skopisjiwa.utils.USER
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    fun register(
        firestore : FirebaseFirestore,
        email : String,
        password : String,
         successToast: () -> Unit,
    ) {
        val userModel = UserModel(
            id = UUID.randomUUID().toString(),
            roleId = 3,
            email = email,
            password = password,
            name = "",
            address = "",
            number = "",
        )
        firestore.collection(USER).add(userModel).addOnSuccessListener {
            successToast()
        }
    }
}