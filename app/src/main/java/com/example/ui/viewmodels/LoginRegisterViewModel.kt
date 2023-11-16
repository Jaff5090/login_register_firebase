package com.example.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginRegisterViewModel : ViewModel() {

    private var _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult
    private var _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun login(email: String?, password: String?) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _loginResult.value = task.isSuccessful
                }
        } else {
            _loginResult.value = false
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _loginResult.value = task.isSuccessful
            }
    }



    fun register(email: String?, password: String?, confirmPass: String?) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return
        }

        if (password == confirmPass) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _registerResult.value = task.isSuccessful
                }
        } else {
            _registerResult.value = false
        }
    }


}
