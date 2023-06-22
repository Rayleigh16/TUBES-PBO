package com.skopisjiwa.presentation.user.register

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
import com.skopisjiwa.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeRegister.setOnClickListener {
            findNavController().navigateUp()
        }

        registerUser()
    }

    private fun registerUser() {
        binding.btnRegister.setOnClickListener {
            with(binding) {
                val email = edtEmailUsername.text?.trim().toString()
                val password = edtPassword.text?.trim().toString()
                val confirmPassword = edtConfirmPassword.text?.trim().toString()

                when {
                    email.isEmpty() -> edtEmailUsername.error = "Field is required"
                    password.isEmpty() -> edtPassword.error = "Field is required"
                    password.length < 6 -> edtEmailUsername.error = "Minimum 6 characters"
                    confirmPassword != password -> edtEmailUsername.error =
                        "Confirm password is not same"

                    else -> register(email, confirmPassword)
                }
            }
        }
    }

    private fun register(email: String, confirmPassword: String) {
        viewModel.register(
            Firebase.firestore,
            email,
            confirmPassword,
        ) {
            Toast.makeText(requireContext(), "Register berhasil", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

}