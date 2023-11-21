package com.example.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login_register_firebase.R
import com.example.login_register_firebase.databinding.ActivityHomeBinding
import com.example.ui.fragments.Home_fragment
import com.example.ui.fragments.Settings_fragment
import com.example.ui.fragments.profile_fragment
import com.example.ui.viewmodels.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Home_fragment())


        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> replaceFragment(Home_fragment())
                R.id.page_2 -> replaceFragment(profile_fragment())
                R.id.page_3 -> replaceFragment(Settings_fragment())
                else -> false
            }
            true
        }


    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

