package com.skopisjiwa.presentation.user.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skopisjiwa.data.cart.CartModel
import com.skopisjiwa.data.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cart  = MutableLiveData<CartModel>()
    val cart : LiveData<CartModel> = _cart

    fun addCart(cartModel: CartModel) {
        _cart.value = cartModel
    }

    fun insertCart(cartModel: CartModel) {
        viewModelScope.launch {
            cartRepository.insertCart(cartModel)
        }

    }
}