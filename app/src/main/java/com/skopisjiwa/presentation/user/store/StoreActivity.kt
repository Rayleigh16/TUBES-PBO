package com.skopisjiwa.presentation.user.store

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.skopisjiwa.R
import com.skopisjiwa.data.store.StoreModel
import com.skopisjiwa.databinding.ActivityStoreBinding
import com.skopisjiwa.presentation.outlet.add.AddEditStoreViewModel
import com.skopisjiwa.presentation.outlet.add.EditOutletActivity
import com.skopisjiwa.presentation.user.product.ListProductActivity
import com.skopisjiwa.presentation.user.store.adapter.StoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreActivity : AppCompatActivity(), StoreAdapter.CellClickListener {

    private lateinit var binding: ActivityStoreBinding
    private var list: List<StoreModel> = emptyList()
    private val adapter: StoreAdapter by lazy {
        StoreAdapter(list.toMutableList(), this, this)
    }

    private val storeViewModel : StoreViewModel by viewModels()

    val viewModel: AddEditStoreViewModel by viewModels()
    private var roleId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roleId = intent.getLongExtra("roleId", 0)

        binding.rvStore.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvStore.setHasFixedSize(true)

        storeViewModel.isLoading.observe(this) {
            binding.progressCircular.isVisible = it
        }

        storeViewModel.stores.observe(this) {
            list = it
            if (roleId == 1L){
                adapter.setDataStore(it.toMutableList())
                viewModel.getStore(it.toMutableList(), adapter)
            } else {
                adapter.setDataStore(it.toMutableList())
            }

        }
        binding.rvStore.adapter = adapter


        if (roleId == 1L) {
            adminFeature()
        }

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }
        adapter.setDataStore(list.toMutableList())
        searchStore()
    }

    private fun adminFeature() {
        with(binding) {
            tvTitleMenu.text = "Outlet"
            adapter.getIdRole(roleId)
            icMap.setImageResource(R.drawable.ic_add_store)
            icMap.setOnClickListener {
                val intent = Intent(this@StoreActivity, EditOutletActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun searchStore() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtering(newText.toString())
                return false
            }
        })
    }

    private fun filtering(newText: String) {
        val filteredList = ArrayList<StoreModel>()

        for (item in list) {
            if (item.titleStore?.lowercase()?.contains(newText.lowercase()) == true) {
                filteredList.add(item)
            }
        }
        storeViewModel.stores.observe(this) { stores ->
            for(item in stores) {
                if(item.titleStore.lowercase().contains(newText.lowercase())) {
                    filteredList.add(item)
                }
            }

            if(filteredList.isEmpty()){
                binding.rvStore.isVisible = false
                binding.tvEmpty.isVisible = true
            } else {
                binding.rvStore.isVisible = true
                binding.tvEmpty.isVisible = false
                adapter.filter(filteredList)
            }
        }
    }

    override fun onCellClickListener(data: StoreModel) {
        if (roleId == 1L) {
            val intent = Intent(this@StoreActivity, EditOutletActivity::class.java)
            intent.putExtra("EXTRA_EDIT", true)
            intent.putExtra("EXTRA_DATA", data)
            startActivity(intent)
        } else {
            val intent = Intent(this@StoreActivity, ListProductActivity::class.java)
            intent.putExtra("data_store", data)
            startActivity(intent)
        }
    }
}