package com.skopisjiwa.presentation.user.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skopisjiwa.R
import com.skopisjiwa.databinding.FragmentLoginBinding
import com.skopisjiwa.presentation.user.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLogin.observe(requireActivity()) { isLogin ->
            if (isLogin == true) {
                findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        with(binding) {
            val email = edtEmailUsername.text?.trim().toString()
            val password = edtPassword.text?.trim().toString()

            when {
                email.isEmpty() -> edtEmailUsername.error = "Field is required"
                password.isEmpty() -> edtPassword.error = "Field is Required"
                password.length < 6 -> edtPassword.error = "Minimal 6 characters"
                else -> login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(firestore = Firebase.firestore, email = email, password = password,
            successToast = { roleId ->
                if (roleId == 1L) {
                    Toast.makeText(
                        requireContext(),
                        "Login berhasil As Admin",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    intent.putExtra("roleId", roleId)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Login berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    intent.putExtra("roleId", roleId)
                    startActivity(intent)
                }
            },
            failureToast = {
                Toast.makeText(
                    requireContext(),
                    "email atau password anda salah",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
}