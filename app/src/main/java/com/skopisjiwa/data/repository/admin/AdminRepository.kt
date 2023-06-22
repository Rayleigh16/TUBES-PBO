package com.skopisjiwa.data.repository.admin

import android.annotation.SuppressLint
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.skopisjiwa.data.store.StoreModel
import com.skopisjiwa.presentation.user.store.adapter.StoreAdapter
import com.skopisjiwa.utils.Resource
import com.skopisjiwa.utils.STORE
import java.util.UUID
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    fun addStore(
        image: String,
        nameStore: String,
        jamOperation: String,
        addressStore: String,
        uiStatus: () -> Unit
    ) {
        val storeModel = StoreModel(
            id = UUID.randomUUID().toString(),
            image = image,
            titleStore = nameStore,
            open = jamOperation,
            address = addressStore,
        )
        val result = firebaseFirestore.collection(STORE).add(storeModel).addOnSuccessListener {
            uiStatus()
        }
    }

    fun editStore(
        id: String,
        storesModel: StoreModel,
        result: (Resource<String>) -> Unit
    ) {
        val document = firebaseFirestore.collection(STORE)
        val query = document.whereEqualTo("id", id)
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentSnapshot = querySnapshot.documents[0]
                    documentSnapshot.reference.update(
                        "titleStore", storesModel.titleStore,
                        "address", storesModel.address,
                        "open", storesModel.open,
                        "image", storesModel.image
                    )

                        .addOnSuccessListener {
                            result.invoke(Resource.Success("Store has been upated"))
                        }
                        .addOnFailureListener { exception ->
                            result.invoke(Resource.Error(exception.localizedMessage))
                        }
                } else {
                    result.invoke(Resource.Error("Store not found"))
                }
            }
            .addOnFailureListener { exception ->
                result.invoke(Resource.Error(exception.localizedMessage))
            }
    }
    fun getStore(
        arrayList: MutableList<StoreModel>,
        storeAdapter: StoreAdapter,
        result: (Resource<List<StoreModel>>) -> Unit
    ) {
        firebaseFirestore.collection(STORE)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        result.invoke(Resource.Error(error.message.toString()))
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        val data = StoreModel(
                            address = dc.document.getString("address").toString(),
                            id = dc.document.getString("id").toString(),
                            image = dc.document.getString("image").toString(),
                            open = dc.document.getString("open").toString(),
                            titleStore = dc.document.getString("titleStore").toString(),
                        )
                        arrayList.add(data)
                    }
                    storeAdapter.notifyDataSetChanged()
                }
            }
            )
    }
}