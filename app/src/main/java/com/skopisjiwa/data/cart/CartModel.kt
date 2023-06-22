package com.skopisjiwa.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartModel(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val nameProduct : String,
    val price : Long,
    val qty : Int
)
