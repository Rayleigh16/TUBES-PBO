package com.skopisjiwa.data.repository.cart

import androidx.lifecycle.LiveData
import com.skopisjiwa.data.cart.CartDao
import com.skopisjiwa.data.cart.CartModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun getAllCarts(): LiveData<List<CartModel>> {
       return cartDao.getAllCart()
    }

    override suspend fun insertCart(cartModel: CartModel) {
         CoroutineScope(Dispatchers.IO).launch{
             cartDao.insertCart(cartModel)
        }
    }

    override suspend fun deleteCart() {
        CoroutineScope(Dispatchers.IO).launch{
            cartDao.deleteCart()
        }
    }


}