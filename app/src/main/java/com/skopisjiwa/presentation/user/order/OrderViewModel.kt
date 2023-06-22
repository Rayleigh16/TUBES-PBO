package com.skopisjiwa.presentation.user.order

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.skopisjiwa.data.TransactionModel
import com.skopisjiwa.utils.TRANSACTION
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    fun postTransaction(
        firestore: FirebaseFirestore,
        transactionModel: TransactionModel,
        navigateSuccess : () -> Unit,
    ){
        firestore.collection(TRANSACTION)
            .add(transactionModel)
            .addOnSuccessListener {
                navigateSuccess()
            }
    }

}