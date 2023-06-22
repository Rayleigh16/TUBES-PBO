package com.skopisjiwa.data

data class TransactionModel(
    val number : String,
    val status : String,
    val typeOrder : String,
    val totalPrice : Long,
    val grandPrice : Long,
)
