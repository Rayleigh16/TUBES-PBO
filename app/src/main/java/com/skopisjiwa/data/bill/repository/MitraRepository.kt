package com.skopisjiwa.data.bill.repository

import android.annotation.SuppressLint
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.skopisjiwa.data.bill.HistoryTransactionModel
import com.skopisjiwa.presentation.history.HistoryAdapter
import com.skopisjiwa.utils.Resource
import javax.inject.Inject

class MitraRepository @Inject constructor(
    val firestore: FirebaseFirestore
) {

    fun getTransaction(
        arrayList: ArrayList<HistoryTransactionModel>,
        storeAdapter: HistoryAdapter,
        result: (Resource<List<HistoryTransactionModel>>) -> Unit
    ) {
        firestore.collection("transaction")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        result.invoke(Resource.Error(error.message.toString()))
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        arrayList.add(dc.document.toObject(HistoryTransactionModel::class.java))
                        result.invoke(Resource.Success(arrayList.filterNotNull()))
                    }
                    storeAdapter.notifyDataSetChanged()
                }
            }
            )
    }
}