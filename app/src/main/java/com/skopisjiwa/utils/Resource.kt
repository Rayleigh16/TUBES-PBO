package com.skopisjiwa.utils

import java.text.NumberFormat
import java.util.Locale

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()

    object Loading : Resource<Nothing>()

    data class Error(
        val message: String
    ) : Resource<Nothing>()
}


object ConvertStringPrice {
    fun convertToCurrencyFormat(price: Int): String {
        val currencyFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        return currencyFormat.format(price.toLong())
    }
}


sealed class EditOrAdd(page: String) {
    data class Edit(val page: String) : EditOrAdd(page)
    data class Add(val page: String) : EditOrAdd(page)
}
