package com.skopisjiwa.data.repository.user

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.skopisjiwa.data.user.UserModel
import com.skopisjiwa.utils.Resource
import com.skopisjiwa.utils.USER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
) {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun login(email: String, password: String): Flow<Resource<QuerySnapshot>> = flow {
        emit(Resource.Loading)

        try {
            val result = firestore.collection(USER)
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()

            emit(result.result.let {
                Resource.Success(data = it)
            })
        } catch (e: Exception) {
            emit(Resource.Error("${e.message}"))
        }
    }

    fun register(email: String, password: String): Flow<Resource<DocumentReference>> =
        flow {
            emit(Resource.Loading)
            try {
                val userModel = UserModel(
                    id = UUID.randomUUID().toString(),
                    roleId = 3,
                    email = email,
                    password = password,
                    name = "",
                    address = "",
                    number = "",
                )
                val result = firestore.collection(USER).add(userModel)

                emit(
                    result.result.let { Resource.Success(data = it) }
                )


            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: ""))
            }
        }

    companion object {
        const val TAG = "UserRepository"
    }


}