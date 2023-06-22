package com.skopisjiwa.presentation.history

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skopisjiwa.data.bill.HistoryTransactionModel
import com.skopisjiwa.databinding.ActivityHistoryBinding
import com.skopisjiwa.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    private lateinit var list: ArrayList<HistoryTransactionModel>

    private lateinit var adapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.rvHistory.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvHistory.setHasFixedSize(true)

        list = arrayListOf()
        adapter = HistoryAdapter(list)
        binding.rvHistory.adapter = adapter
        observe()
        viewModel.getDataKegiatan(list, adapter)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun observe() {
        viewModel.kegiatan.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    Log.d("loading", "loading")
                }

                is Resource.Error -> {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    Toast.makeText(this, "Successe Load Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}