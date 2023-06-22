package com.skopisjiwa.presentation.user.order

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.skopisjiwa.databinding.ActivityOrderFinishBinding
import com.skopisjiwa.presentation.user.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class OrderFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderFinishBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderNumber = intent.getStringExtra("order_number")
        val transactionId = intent.getStringExtra("transaction_id")

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy ")
        val formatted = current.format(formatter)

        binding.textView27.text = formatted
        binding.tvOrderNumber.text = orderNumber
        binding.tvTransactionId.text = transactionId

        binding.btnOrderPage.setOnClickListener {
            val intent = Intent(this@OrderFinishActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}