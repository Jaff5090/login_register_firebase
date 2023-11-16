package com.example.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SettingsViewModel : ViewModel(){
    private var _signOutResult = MutableLiveData<Boolean>()
    val signOutResult: LiveData<Boolean> = _signOutResult

    fun signOut() {
        Firebase.auth.signOut()
        _signOutResult.value = true
    }
}