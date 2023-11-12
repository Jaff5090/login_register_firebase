package com.example.login_register_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewmodels.SplashScreenViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
