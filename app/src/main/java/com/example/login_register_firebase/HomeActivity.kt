package com.example.login_register_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login_register_firebase.databinding.ActivityHomeBinding
import com.example.login_register_firebase.databinding.FragmentLoginBinding
import com.example.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.logout.setOnClickListener {
            viewModel.signOut()
        }

        viewModel.signOutResult.observe(this, Observer { isSignedOut ->
            if (isSignedOut) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

