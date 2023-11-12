package com.example.login_register_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodels.SplashScreenViewModel

class SplashScreen : AppCompatActivity() {

    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_splash_screen)


        viewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

        viewModel.signOutResult.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                Log.d("MainActivity", "")
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("MainActivity", "Non connecté ou déconnexion réussie")
                val intent = Intent(this, MainActivity::class.java) // Remplacez par votre activité de connexion
                startActivity(intent)
                finish()
            }
        })


        viewModel.checkUserStatus()
    }

}