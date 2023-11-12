package com.example.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashScreenViewModel : ViewModel() {
    private val _signOutResult = MutableLiveData<Boolean>()
    val signOutResult: LiveData<Boolean> = _signOutResult

    fun checkUserStatus() {
        val user = Firebase.auth.currentUser
        _signOutResult.value = user != null
    }
}
