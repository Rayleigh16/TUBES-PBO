package com.skopisjiwa.presentation.user.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import com.skopisjiwa.MainActivity
import com.skopisjiwa.databinding.ActivityHomeBinding
import com.skopisjiwa.presentation.history.HistoryActivity
import com.skopisjiwa.presentation.user.store.StoreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.roleId.observe(this) {roleId ->
            Log.d("HomeActivity",roleId.toString())
            when (roleId) {
                1L -> {

                    adminMenu(roleId)
                }
                2L -> {   //customer
                    binding.apply {
                        mitraMenu(roleId)
                    }
                }
                else -> {  // mitra
                    binding.apply {
                        btnPickUp.setOnClickListener {
                            val intent = Intent(this@HomeActivity, StoreActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        Log.d("HomeActivity", "ViewModel: $viewModel")


        binding.btnSignOut.setOnClickListener {
            viewModel.setIsLogin(false)
            viewModel.setRoleId(0L)
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun adminMenu(roleId : Long) {
        binding.btnPickUp.text = "Outlet"
        binding.tvUserName.text = "Admin"
        val layoutParam = binding.btnPickUp.layoutParams as ViewGroup.MarginLayoutParams
        layoutParam.topMargin = convertDpToPx(100)
        binding.btnDriveThru.visibility = View.GONE

        // to list store
        binding.btnPickUp.setOnClickListener {
            val intent = Intent(this@HomeActivity, StoreActivity::class.java)
            intent.putExtra("roleId", roleId)
            startActivity(intent)
        }
    }

    private fun convertDpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    private fun mitraMenu(roleId: Long){
        with(binding){
            tvUserName.text = "Mitra"
            btnPickUp.text = "Cashier"
            btnDriveThru.text = "Menu"
            btnPickUp.setOnClickListener {
                    val intent = Intent(this@HomeActivity, HistoryActivity::class.java)
                intent.putExtra("roleId", roleId)
                startActivity(intent)
            }
        }
    }
}