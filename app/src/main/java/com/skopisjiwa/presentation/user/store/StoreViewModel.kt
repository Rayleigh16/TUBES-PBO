package com.skopisjiwa.presentation.user.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skopisjiwa.data.store.StoreModel
import com.skopisjiwa.utils.STORE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor() : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _stores = MutableLiveData<MutableList<StoreModel>>(arrayListOf())
    val stores: LiveData<MutableList<StoreModel>> = _stores

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    init {
        getStore(Firebase.firestore)
    }

    private fun getStore(firestore: FirebaseFirestore) {
        _isLoading.value = true
        firestore.collection(STORE).get()
            .addOnCompleteListener {
                val list = arrayListOf<StoreModel>()
                if (it.isSuccessful) {
                   for (store in it.result){
                       list.add(
                           StoreModel(
                               address = store.getString("address").toString(),
                               id = store.getString("id").toString(),
                               image = store.getString("image").toString(),
                               open = store.getString("open").toString(),
                               titleStore = store.getString("titleStore").toString(),

                           )
                       )
                   }
                }
                _stores.value = list
                _isLoading.value = false
            }
            .addOnFailureListener {
                _message.value = "Terdapat gangguan pada server"
            }
    }
}