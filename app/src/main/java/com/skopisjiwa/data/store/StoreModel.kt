package com.skopisjiwa.data.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel(
    val address: String,
    val id : String,
    val image: String,
    val open: String,
    val titleStore: String,
):Parcelable
