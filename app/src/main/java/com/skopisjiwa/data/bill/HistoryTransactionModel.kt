package com.skopisjiwa.data.bill

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryTransactionModel(
    val number: String? = "",
    val grandPrice: Long? = null,
    val status: String? = null,
    val totalPrice: Long? = null,
    val typeOrder: String? = ""
) : Parcelable
