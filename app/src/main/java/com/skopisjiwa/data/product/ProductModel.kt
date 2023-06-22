package com.skopisjiwa.data.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val nameProduk: String,
    val gambarProduk: Int,
    val hargaProduk: Int,
    val description:String
) : Parcelable

@Parcelize
data class OptionRadioButton(
    val size: String,
    val price: Int,
) : Parcelable

@Parcelize
data class OrderSummaryModel(
    val number: Int,
    val nameProduk: String,
    val hargaProduk: Long,
    val sizeProduk: String? = "",
    val tambahanHargaSize: Int? = 0,
    val availableProduk: String?= "",
    val sugarLevelDrink: String? = ""
) : Parcelable