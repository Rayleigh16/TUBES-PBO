package com.skopisjiwa.presentation.user.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skopisjiwa.data.cart.CartModel
import com.skopisjiwa.data.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel(){

    val carts : LiveData<List<CartModel>> = cartRepository.getAllCarts()

    fun deleteCarts() {
        viewModelScope.launch {
            cartRepository.deleteCart()
        }
    }

}