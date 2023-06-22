package com.skopisjiwa.presentation.user.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.skopisjiwa.R
import com.skopisjiwa.data.product.ProductModel
import com.skopisjiwa.data.store.StoreModel
import com.skopisjiwa.databinding.ActivityListProductBinding
import com.skopisjiwa.presentation.user.order.OrderActivity
import com.skopisjiwa.presentation.user.product.adapter.ProductAdapter
import com.skopisjiwa.utils.ConvertStringPrice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductActivity : AppCompatActivity(), ProductAdapter.CellClickListener {


    private lateinit var binding: ActivityListProductBinding
    private lateinit var received: StoreModel
    private lateinit var adapter: ProductAdapter
    private lateinit var list: ArrayList<ProductModel>

    private val viewModel by viewModels<ListProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = arrayListOf(
            ProductModel(
                "Air jiwa",
                R.drawable.produk_late, 12000,
                "minuman yang membuat and segar"
            ),
            ProductModel(
                "Air jiwa",
                R.drawable.produk_late, 12000,
                "minuman yang membuat and happy"
            ),
            ProductModel(
                "Kopi Susu",
                R.drawable.kopi_susu, 25000,
                "minuman yang membuat and keyang"
            ),
            ProductModel(
                "Air Jiwa",
                R.drawable.produk_late, 12000,
                "minuman yang membuat and goyang"
            ),
            ProductModel(
                "Kopi Susu Wuenak",
                R.drawable.kopi_susu, 35000,
                "minuman yang membuat and enjoy"
            ),
            ProductModel(
                "Kopi Susu Wuenak",
                R.drawable.kopi_susu, 35000,
                "minuman yang membuat and segar"
            ),
            ProductModel(
                "Kopi Susu Wuenak",
                R.drawable.kopi_susu, 35000,
                "minuman yang membuat and segar"
            ),
            ProductModel(
                "Kopi Susu Wuenak",
                R.drawable.kopi_susu, 35000,
                "minuman yang membuat and segar"
            ),
            ProductModel(
                "Kopi Susu Wuenak",
                R.drawable.kopi_susu, 35000,
                "minuman yang membuat and segar"
            ),
        )


        val intent: Intent = intent
        received = intent.getParcelableExtra("data_store")!!


        binding.tvLocation.text = received.titleStore
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

//        new release
        binding.rvNewRelease.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvNewRelease.setHasFixedSize(true)
        adapter = ProductAdapter(list, this)
        binding.rvNewRelease.adapter = adapter


//        kopi signature
        binding.rvKopiSignature.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvKopiSignature.setHasFixedSize(true)
        adapter = ProductAdapter(list, this)
        binding.rvKopiSignature.adapter = adapter

//        non cofee
        binding.rvNonCofee.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvNonCofee.setHasFixedSize(true)
        adapter = ProductAdapter(list, this)
        binding.rvNonCofee.adapter = adapter

//        Snack
        binding.rvSnack.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvSnack.setHasFixedSize(true)
        adapter = ProductAdapter(list, this)
        binding.rvSnack.adapter = adapter

        viewModel.carts.observe(this) { carts ->
            Log.d("ListProductActivity", "list carts : $carts")
            with(binding) {
                if (carts.isNotEmpty()) {
                    constraintLayout9.isVisible = true
                    tvItemCount.text = carts.size.toString()
                    val convertPrice =
                        ConvertStringPrice.convertToCurrencyFormat(carts.sumOf { it.price }.toInt())
                    tvItemTotalPrice.text = convertPrice
                } else {
                    constraintLayout9.isVisible = false
                }
            }
        }

        binding.btnToOrderChart.setOnClickListener {
            val intent = Intent(this@ListProductActivity, OrderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCellClickListener(data: ProductModel) {
        val intent = Intent(this@ListProductActivity, DetailProductActivity::class.java)
        intent.putExtra("data_produk", data)
        startActivity(intent)
    }
}