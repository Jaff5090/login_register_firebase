package com.example.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ui.activities.HomeActivity
import com.example.login_register_firebase.R
import com.example.login_register_firebase.databinding.FragmentRegisterBinding
import com.example.ui.viewmodels.LoginRegisterViewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginRegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.connecterVous.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        viewModel = ViewModelProvider(this).get(LoginRegisterViewModel::class.java)

        binding.ConnexionRegister.setOnClickListener {
            val email = binding.emailEditTextRegister.text.toString().trim()
            val password = binding.passwordEditTextRegister.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditTextRegister.text.toString().trim()


            viewModel.register(email, password,confirmPassword)
        }
        viewModel.registerResult.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Log.d("LoginFragment", "Connexion réussie")
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Log.d("LoginFragment", "Échec de la connexion")
                Toast.makeText(activity, "Échec de la connexion", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}