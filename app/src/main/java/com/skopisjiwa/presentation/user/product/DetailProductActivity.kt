package com.skopisjiwa.presentation.user.product

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skopisjiwa.data.cart.CartModel
import com.skopisjiwa.data.product.OptionRadioButton
import com.skopisjiwa.data.product.ProductModel
import com.skopisjiwa.databinding.ActivityDetailProductBinding
import com.skopisjiwa.presentation.user.product.adapter.RadioButtonAdapter
import com.skopisjiwa.utils.ConvertStringPrice
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private lateinit var received: ProductModel
    private lateinit var radioButtonAdapter: RadioButtonAdapter
    private lateinit var radioButonSize: ArrayList<OptionRadioButton>
    private lateinit var radioAvailable: ArrayList<OptionRadioButton>
    private lateinit var radioSugarLevel: ArrayList<OptionRadioButton>
    private val viewModel by viewModels<ProductViewModel>()


    private var quantity: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        received = intent.getParcelableExtra("data_produk")!!

        viewModel.addCart(
            cartModel = CartModel(
                nameProduct = received.nameProduk,
                price = received.hargaProduk.toLong(),
                qty = quantity
            )
        )

        initView()

        radioButonSize = arrayListOf(
            OptionRadioButton("Small", 0),
            OptionRadioButton("Medium", 3000),
            OptionRadioButton("Large", 4000),
        )


        radioAvailable = arrayListOf(
            OptionRadioButton("Hot", 0),
            OptionRadioButton("Cold", 2000),
        )


        radioSugarLevel = arrayListOf(
            OptionRadioButton("Less sugar", 0),
            OptionRadioButton("Normal", 0),
        )

//        size option
        binding.rvOptionSize.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvOptionSize.setHasFixedSize(true)
        radioButtonAdapter = RadioButtonAdapter(radioButonSize)
        binding.rvOptionSize.adapter = radioButtonAdapter


//        option available
        binding.rvOptionAvailable.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvOptionAvailable.setHasFixedSize(true)
        radioButtonAdapter = RadioButtonAdapter(radioAvailable)
        binding.rvOptionAvailable.adapter = radioButtonAdapter


//option sugar level
        binding.rvOptionSugar.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvOptionSugar.setHasFixedSize(true)
        radioButtonAdapter = RadioButtonAdapter(radioSugarLevel)
        binding.rvOptionSugar.adapter = radioButtonAdapter


        binding.btnCloseDetail.setOnClickListener {
            onBackPressed()
        }

        observeCart()
        inputPriceToCart()
    }

    private fun initView() {
        with(binding) {
            tvNameProduct.text = received.nameProduk
            tvDescriptionProduct.text = received.description
            Picasso.get().load(received.gambarProduk).into(imageView5)
        }
    }

    private fun observeCart(){
        viewModel.cart.observe(this) { cart ->
            //        get selectedRadionButtonSize
            val selectedPositionSize = radioButtonAdapter.getSelectedPosition()
            if (selectedPositionSize != -1) {
                val seletedSize = radioButonSize[selectedPositionSize]
                Log.d("DetailProductActivity", seletedSize.toString())
                val newPrice = cart.copy(
                    price = cart.price + 2000
                )
                viewModel.addCart(newPrice)

                val hargaTambahan = seletedSize.price
//            doing action with harga tambahan  to order addchart total

            }


            //        get selectedRadionButtonAvailable
            val selectedPositioAvailable = radioButtonAdapter.getSelectedPosition()
            if (selectedPositioAvailable != -1) {
                val seletedAvailable = radioAvailable[selectedPositioAvailable]
                val newPrice = cart.copy(
                    price = (received.hargaProduk + seletedAvailable.price).toLong()
                )
                viewModel.addCart(newPrice)

                val hargaTambahan = seletedAvailable.price
//            doing action with harga tambahan  to order addchart total

            }


            //        get selectedRadionButton sugar level
            val selectedPositionSugar = radioButtonAdapter.getSelectedPosition()
            if (selectedPositionSugar != -1) {
                val seletedSize = radioSugarLevel[selectedPositionSugar]
                val newPrice = cart.copy(
                    price = (received.hargaProduk + seletedSize.price).toLong()
                )
                viewModel.addCart(newPrice)

                val hargaTambahan = seletedSize.price
//            doing action with harga tambahan  to order addchart total
            }


            val convertPrice = ConvertStringPrice.convertToCurrencyFormat(cart.price.toInt())
            binding.tvTotal.text = convertPrice


//      increase dicrease item produk
            binding.btnPlusQuntity.setOnClickListener {
                Log.d("DetailProductActivity", cart.toString())
                quantity++
                binding.tvTotalItem.text = quantity.toString()
                val newCart = cart.copy(
                    qty = quantity,
                    price = (received.hargaProduk * quantity).toLong()
                )
                viewModel.addCart(newCart)
            }

            binding.btnMinusQuantity.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    binding.tvTotalItem.text = quantity.toString()
                    val newCart = cart.copy(
                        qty = quantity,
                        price = (received.hargaProduk * quantity).toLong()
                    )
                    viewModel.addCart(newCart)
                } else {
                    quantity = 1
                    val newCart = cart.copy(
                        qty = quantity,
                        price = (received.hargaProduk * quantity).toLong()
                    )
                    viewModel.addCart(newCart)
                }
            }

            binding.btnToChart.setOnClickListener {
                Log.d("DetailProductActivity", "cart item : $cart")
                viewModel.insertCart(cartModel = cart)
                finish()
            }
        }
    }

    private fun inputPriceToCart() {

    }
}