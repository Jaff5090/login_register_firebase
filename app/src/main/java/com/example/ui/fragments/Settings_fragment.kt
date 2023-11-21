package com.example.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login_register_firebase.R
import com.example.login_register_firebase.databinding.FragmentLoginBinding
import com.example.login_register_firebase.databinding.FragmentSettingsFragmentBinding
import com.example.ui.activities.MainActivity
import com.example.ui.viewmodels.HomeViewModel
import com.example.ui.viewmodels.SettingsViewModel


class Settings_fragment : Fragment() {

    private var _binding:FragmentSettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

       binding.LinearLougout.setOnClickListener {
           viewModel.signOut()
       }

        viewModel.signOutResult.observe(viewLifecycleOwner, Observer { isSignedOut ->
            if (isSignedOut) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

    }

}