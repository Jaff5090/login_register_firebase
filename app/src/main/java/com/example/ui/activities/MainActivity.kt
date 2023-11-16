package com.example.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login_register_firebase.R
import com.example.ui.viewmodels.SplashScreenViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
