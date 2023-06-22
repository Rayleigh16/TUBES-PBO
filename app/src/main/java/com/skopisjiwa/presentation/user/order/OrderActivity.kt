package com.skopisjiwa.presentation.user.order

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skopisjiwa.R
import com.skopisjiwa.data.TransactionModel
import com.skopisjiwa.data.order.PaymentSpinner
import com.skopisjiwa.data.product.OrderSummaryModel
import com.skopisjiwa.databinding.ActivityOrderBinding
import com.skopisjiwa.presentation.user.order.adapter.PaymentSpinnerAdapter
import com.skopisjiwa.presentation.user.order.adapter.SummaryAdapter
import com.skopisjiwa.presentation.user.product.ListProductViewModel
import com.skopisjiwa.utils.ConvertStringPrice
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {

    private lateinit var summaryAdapter: SummaryAdapter
    private lateinit var listSummary: ArrayList<OrderSummaryModel>
    private lateinit var paymentAdapter: PaymentSpinnerAdapter
    private lateinit var list: ArrayList<PaymentSpinner>
    private lateinit var binding: ActivityOrderBinding

    private val viewModel by viewModels<ListProductViewModel>()
    private val transactionViewModel by viewModels<OrderViewModel>()
    val newList = arrayListOf<OrderSummaryModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListPayment()

        val spinner = binding.spinner
        paymentAdapter = PaymentSpinnerAdapter(this, list)
        spinner.adapter = paymentAdapter


        //        configure payemnt method
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val clickedItem = parent?.getItemAtPosition(position) as PaymentSpinner
                val clickedPaymentName = clickedItem.paymentName
                Toast.makeText(
                    this@OrderActivity,
                    "$clickedPaymentName selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        //       end configure payemnt method


//        sumarry prodcut item rv
//        DUMMY ORDER DATA

//        listSummary =
//            arrayListOf(
//                OrderSummaryModel(1, "aqua", 12000, "Medium", 12000, "Cold", "low"),
//                OrderSummaryModel(2, "Mineral", 12000, "Medium", 12000, "Cold", "low")
//            )


        viewModel.carts.observe(this) { carts ->
            var list = arrayListOf<Any>()
            for (i in carts) {
                list.add(i)
            }
            Log.d("totalnya_beropo", list.toString())

            for (item in carts) {
                val orderSummary = OrderSummaryModel(
                    number = item.id,
                    nameProduk = item.nameProduct,
                    hargaProduk = item.price
                )
                newList.add(orderSummary)
            }

            val convertPrice =
                ConvertStringPrice.convertToCurrencyFormat(carts.sumOf { it.price }.toInt())
            binding.tvSubtotalPrice.text = convertPrice
            binding.tvTotalPayments.text = convertPrice

        }

        listSummary = newList

        binding.rvOrderSummary.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvOrderSummary.setHasFixedSize(true)
        summaryAdapter = SummaryAdapter(listSummary)
        binding.rvOrderSummary.adapter = summaryAdapter

        //       end  sumarry prodcut item rv
        binding.btnPlaceOrder.setOnClickListener {
            viewModel.deleteCarts()

            val transaction = TransactionModel(
                number = UUID.randomUUID().toString(),
                status = "Lunas",
                typeOrder = "Pick Up",
                totalPrice = listSummary.sumOf { it.hargaProduk }.toLong(),
                grandPrice = listSummary.sumOf { it.hargaProduk }.toLong(),
            )
            transactionViewModel.postTransaction(
                firestore = Firebase.firestore,
                transactionModel = transaction
            ) {
                val intent = Intent(this@OrderActivity, OrderFinishActivity::class.java).apply {
                    putExtra("order_number", transaction.number.substring(0, 4))
                    putExtra("transaction_id", transaction.number.substring(transaction.number.length - 4, transaction.number.length))
                }
                startActivity(intent)
            }
        }


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initListPayment() {
        list = ArrayList()
        list.add(PaymentSpinner("Dana", R.drawable.ic_dana))
        list.add(PaymentSpinner("Ovo", R.drawable.ic_ovo))
        list.add(PaymentSpinner("Flip", R.drawable.ic_flip))
        list.add(PaymentSpinner("Cash", R.drawable.ic_cash))
        list.add(PaymentSpinner("Link Aja", R.drawable.ic_linkaja))

    }
}