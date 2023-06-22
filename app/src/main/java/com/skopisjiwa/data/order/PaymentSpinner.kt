package com.skopisjiwa.data.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentSpinner(
    val paymentName: String,
    val logoPayment: Int
) : Parcelable
