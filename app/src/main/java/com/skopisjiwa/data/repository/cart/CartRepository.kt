package com.skopisjiwa.data.repository.cart

import androidx.lifecycle.LiveData
import com.skopisjiwa.data.cart.CartModel

interface CartRepository {
    fun getAllCarts() : LiveData<List<CartModel>>
    suspend fun insertCart(cartModel: CartModel)
    suspend fun deleteCart()
}